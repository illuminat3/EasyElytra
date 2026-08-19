package com.illuminat3.easyelytra.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.Vec3;

public class ElytraControlHandler {
    public static void onTick(Minecraft client) {
        if (client.player != null && client.player.isFallFlying()) {
            controlElytra(client.player, client.options);
        }
    }

    private static final double speed = 0.05;

    private static void controlElytra(LocalPlayer player, Options options) {
        double maxSpeed = 2.5;

        if (options.keyUp.isDown()) {
            Vec3 forward = player.getViewVector(1.0F).normalize().scale(speed);
            player.addDeltaMovement(forward);
        }
        if (options.keyDown.isDown()) {
            Vec3 backward = player.getViewVector(1.0F).normalize().scale(-speed);
            if (isMovingForwardWithVelocity(player)) {
                player.addDeltaMovement(backward);
            }
        }
        if (options.keyLeft.isDown()) {
            player.addDeltaMovement(new Vec3(Math.cos(player.getViewYRot(1.0F) * Math.PI / 180.0) * speed, 0, Math.sin(player.getViewYRot(1.0F) * Math.PI / 180.0) * speed));
        }
        if (options.keyRight.isDown()) {
            player.addDeltaMovement(new Vec3(Math.cos(player.getViewYRot(1.0F) * Math.PI / 180.0) * -speed, 0, Math.sin(player.getViewYRot(1.0F) * Math.PI / 180.0) * -speed));
        }
        if (options.keyJump.isDown()) {
            player.addDeltaMovement(new Vec3(0, speed, 0));
        }
        if (options.keyShift.isDown()) {
            player.addDeltaMovement(new Vec3(0, -speed, 0));
        }

        double velocity = Math.sqrt(player.getDeltaMovement().x * player.getDeltaMovement().x + player.getDeltaMovement().z * player.getDeltaMovement().z);
        if (velocity > maxSpeed) {
            player.setDeltaMovement(player.getDeltaMovement().normalize().scale(maxSpeed));
        }
    }

    private static boolean isMovingForwardWithVelocity(LocalPlayer player) {
        Vec3 velocity = player.getDeltaMovement();
        double velocityLength = velocity.length();
        Vec3 lookDirection = player.getViewVector(1.0F);
        Vec3 normalizedVelocity = velocity.normalize();
        boolean isMovingForward = normalizedVelocity.dot(lookDirection) > 0.99;
        boolean hasCorrectVelocity = Math.abs(velocityLength - speed) < 0.001;

        return isMovingForward && hasCorrectVelocity;
    }
}
