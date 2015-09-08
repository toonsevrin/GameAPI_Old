package com.exorath.game.api.nms.v1_8_R3;

import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

import net.minecraft.server.v1_8_R3.EnumProtocol;
import net.minecraft.server.v1_8_R3.EnumProtocolDirection;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.PacketListener;

public class FakePlayerNetworkManager extends NetworkManager {

    public FakePlayerNetworkManager() {
        super(EnumProtocolDirection.CLIENTBOUND);
    }

    public static AtomicInteger a = new AtomicInteger();
    public static AtomicInteger b = new AtomicInteger();
    public Socket socket;
    public static int[] c = new int[256];
    public static int[] d = new int[256];
    public int e = 0;

    @Override
    public void a(EnumProtocol protocol) {
    }

    @Override
    public void a(PacketListener listener) {
    }

    @Override
    public void a() {
    }

}
