package taichiCarpet.network.packetHandlers;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import taichiCarpet.client.TaichiCarpetClient;
import taichiCarpet.network.RegistarPackets;

import java.util.ArrayList;
import java.util.List;

import static taichiCarpet.TaichiCarpetExtension.*;

public class Handshake {
    public static List<ServerPlayerEntity> players = new ArrayList<ServerPlayerEntity>();
    public static void serverHandler(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf packetbb, PacketSender sender) {
        String version = packetbb.readString();
        LOGGER.info("[taichi-carpet(SERVER)] {} logged in with {}", player.getDisplayName().getString(), version);

        players.add(player);

        PacketByteBuf sendPacketbb =  new PacketByteBuf(Unpooled.buffer());
        sender.sendPacket(RegistarPackets.onClient.HANDSHAKE, sendPacketbb);
    }
    public static void clientHandler(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf packetbb, PacketSender sender) {
        TaichiCarpetClient.ConnectedServer = true;
        LOGGER.info("[taichi-carpet(CLIENT)] logged in server");
    }
}