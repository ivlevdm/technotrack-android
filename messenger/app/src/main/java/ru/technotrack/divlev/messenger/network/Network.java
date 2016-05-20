package ru.technotrack.divlev.messenger.network;


public class Network {
    private static Network network;

    private Network() {}

    public static Network instance() {
        if (network == null) {
            network = new Network();
        }
        return network;
    }
}
