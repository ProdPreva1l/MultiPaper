package puregero.multipaper.database.handlers;

import puregero.multipaper.databasemessagingprotocol.messages.databasebound.WriteStatsMessage;
import puregero.multipaper.databasemessagingprotocol.messages.serverbound.BooleanMessageReply;
import puregero.multipaper.database.FileLocker;
import puregero.multipaper.database.ServerConnection;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class WriteStatsHandler {
    public static void handle(ServerConnection connection, WriteStatsMessage message) {
        CompletableFuture.runAsync(() -> {
            try {
                FileLocker.writeBytes(new File(new File(message.world, "stats"), message.uuid + ".json"), message.data);
                connection.sendReply(new BooleanMessageReply(true), message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}