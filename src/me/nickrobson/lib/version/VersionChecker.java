package me.nickrobson.lib.version;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Nick Robson
 */
public class VersionChecker {

    public static enum VersionFormat {
        XML,
        JSON;
    }

    public static class VersionResult {

        public static final VersionResult CURRENT = new VersionResult(true);
        public static final VersionResult FAILED = new VersionResult("Failed to retrieve version information");
        public static final VersionResult UNSUPPORTED_FORMAT = new VersionResult("Unsupported Version Format");

        private boolean current = false;

        private int versionNumber = 0;
        private String name = null, version = null, url = null, minecraftVersion = null;
        private List<String> notes = null;

        private String error = null;

        public VersionResult(String name, int versionNumber, String version, String minecraftVersion, String url,
                List<String> notes) {
            this.name = name;
            this.versionNumber = versionNumber;
            this.version = version;
            this.minecraftVersion = minecraftVersion;
            this.url = url;
            this.notes = notes;
        }

        protected VersionResult(String error) {
            this.error = error;
        }

        protected VersionResult(boolean current) {
            this.current = current;
        }

        public boolean isSuccess() {
            return this.error == null;
        }

        public boolean isCurrent() {
            return this.current && this.isSuccess();
        }

        public String getError() {
            return this.error;
        }

        public String getVersionName() {
            return this.name;
        }

        public int getVersionNumber() {
            return this.versionNumber;
        }

        public String getVersion() {
            return this.version;
        }

        public String getMinecraftVersion() {
            return this.minecraftVersion;
        }

        public String getUpdateURL() {
            return this.url;
        }

        public List<String> getUpdateNotes() {
            return this.notes;
        }

        @Override
        public String toString() {
            if (this.isSuccess()) {
                if (this.isCurrent()) {
                    return "VersionResult{current=true}";
                }
                return "VersionResult{name=" + this.name + ",number=" + this.versionNumber + ",version=" + this.version
                        + ",mcversion="
                        + this.minecraftVersion + ",url=" + this.url + "}";
            }
            return "VersionResult{error=" + this.error + "}";
        }
    }

    public static enum VersionBranch {

        DEV,
        ALPHA,
        BETA,
        PRE_RELEASE,
        RELEASE;

        public boolean isMoreStable(VersionBranch branch) {
            return branch.ordinal() >= this.ordinal();
        }

        public static VersionBranch from(String string, boolean overrideNull) {
            try {
                return VersionBranch.valueOf(string.toUpperCase().replaceAll(" ", "_"));
            } catch (Exception ex) {
                return overrideNull ? RELEASE : null;
            }
        }
    }

    public static interface VersionCheckCallback {

        /**
         * This function is called when a {@link VersionCheckThread} has
         * finished checking for an
         * update and has a result.
         *
         * @param result
         *            The version check result.
         */
        public void result(VersionResult result);

    }

    protected static class VersionCheckThread extends Thread {

        String surl;
        VersionFormat format;
        VersionBranch branch;
        String minecraftVersion;
        long lastCheck;
        int currentVersionNumber;
        VersionCheckCallback callback;

        VersionResult result;

        protected VersionCheckThread(String surl, VersionFormat format, VersionBranch branch, String minecraftVersion,
                long lastCheck,
                int currentVersionNumber, VersionCheckCallback callback) {
            this.surl = surl;
            this.format = format;
            this.branch = branch;
            this.minecraftVersion = minecraftVersion;
            this.lastCheck = lastCheck;
            this.currentVersionNumber = currentVersionNumber;
            this.callback = callback;
        }

        public VersionResult getResult() {
            return this.result;
        }

        @Override
        public void run() {
            URL url;
            try {
                url = new URL(this.surl);
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
                this.result = VersionResult.FAILED;
                return;
            }
            int versionNumber = 0;
            String name = null, version = null, updateURL = null, minecraftVersion = null;
            List<String> notes = new LinkedList<>();
            if (this.format == VersionFormat.XML) {
                try {
                    Document xml = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url.openStream());
                    NodeList nodes = xml.getChildNodes();
                    for (int i = 0; i < nodes.getLength(); i++) {
                        Node node = nodes.item(i);
                        if (node.getNodeName().equals("branch") && node instanceof Element) {
                            Element element = (Element) node;
                            VersionBranch vb = VersionBranch.from(element.getAttribute("type"), true);
                            minecraftVersion = element.getAttribute("mcversion");
                            if (minecraftVersion.equals(this.minecraftVersion)) {
                                if (vb.isMoreStable(this.branch)) {
                                    this.branch = vb;
                                    Node child = node.getFirstChild();
                                    while (child != null) {
                                        if (child.getNodeName().equals("name")) {
                                            name = child.getTextContent();
                                        } else if (child.getNodeName().equals("version")) {
                                            version = child.getTextContent();
                                        } else if (child.getNodeName().equals("versionNumber")) {
                                            versionNumber = Integer.parseInt(child.getTextContent());
                                        } else if (child.getNodeName().equals("update")) {
                                            updateURL = child.getTextContent();
                                        } else if (child.getNodeName().equals("updateNotes")) {
                                            notes.add(child.getTextContent());
                                        }
                                        child = child.getNextSibling();
                                    }
                                }
                            }
                        }
                    }
                } catch (SAXException | IOException | ParserConfigurationException | IllegalArgumentException ex) {
                    ex.printStackTrace();
                    this.result = VersionResult.FAILED;
                }
            } else if (this.format == VersionFormat.JSON) {
                try {
                    Object element = new JSONParser().parse(new InputStreamReader(url.openStream()));
                    JSONArray array = (JSONArray) element;
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject object = (JSONObject) array.get(i);
                        VersionBranch vb = VersionBranch.from((String) object.get("branch"), true);
                        if (vb.isMoreStable(this.branch)) {
                            this.branch = vb;
                            name = (String) object.get("name");
                            version = (String) object.get("version");
                            versionNumber = (int) object.get("versionNumber");
                            minecraftVersion = (String) object.get("mcversion");
                            updateURL = (String) object.get("update");
                            Object notesObject = object.get("updateNotes");
                            if (notesObject != null) {
                                JSONArray notesArray = (JSONArray) notesObject;
                                for (int x = 0; x < notesArray.size(); x++) {
                                    notes.add((String) notesArray.get(i));
                                }
                            }
                        }
                    }
                } catch (ClassCastException | ParseException | IOException e) {
                    e.printStackTrace();
                    this.result = VersionResult.FAILED;
                }
            } else {
                this.result = VersionResult.UNSUPPORTED_FORMAT;
            }

            if (versionNumber > this.currentVersionNumber) {
                this.result = new VersionResult(name, versionNumber, version, minecraftVersion, updateURL, notes);
            } else {
                this.result = VersionResult.CURRENT;
            }
            this.callback.result(this.result);
        }
    }

    public static void check(String surl, VersionFormat format, VersionBranch branch, String minecraftVersion,
            long lastCheck, boolean forceCheck,
            int currentVersionNumber, VersionCheckCallback callback) {
        if (System.currentTimeMillis() - lastCheck < 1000 * 60 * 30 && !forceCheck) {// only check once every 30 minutes (at most)
            return;
        }
        VersionCheckThread thread = new VersionCheckThread(surl, format, branch, minecraftVersion, lastCheck,
                currentVersionNumber, callback);
        thread.start();
    }
}
