package it.magical.magicam.shared.net;

import androidx.annotation.NonNull;

import java.io.IOException;

public class CommandFailedException extends IOException {
    private int code;

    public CommandFailedException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @NonNull
    @Override
    public String toString() {
        return "CommandFailedException: Code: " + code + "; Message: " + getMessage();
    }
}
