package com.illuminat3.easyelytra.events;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class ElytraControlHandler {
    public static void onTick(MinecraftClient client) {
        if (client.player != null && client.player.isFallFlying()) {
            controlElytra(client.player);
        }
    }

    private static final double speed = 0.05;

    private static void controlElytra(ClientPlayerEntity player) {
        double maxSpeed = 2.5;

        if (player.input.pressingForward) {
            Vec3d forward = Vec3d.fromPolar(player.getPitch(1.0F), player.getYaw(1.0F)).normalize().multiply(speed);
            player.addVelocity(forward.x, forward.y, forward.z);
        }
        if (player.input.pressingBack) {
            Vec3d backward = Vec3d.fromPolar(player.getPitch(1.0F), player.getYaw(1.0F)).normalize().multiply(-speed);
            if (isMovingForwardWithVelocity(player)) {
                player.addVelocity(backward.x, backward.y, backward.z);
            }
        }
        if (player.input.pressingLeft) {
            player.addVelocity(Math.cos(player.getYaw(1.0F) * Math.PI / 180.0) * speed, 0, Math.sin(player.getYaw(1.0F) * Math.PI / 180.0) * speed);
        }
        if (player.input.pressingRight) {
            player.addVelocity(Math.cos(player.getYaw(1.0F) * Math.PI / 180.0) * -speed, 0, Math.sin(player.getYaw(1.0F) * Math.PI / 180.0) * -speed);
        }
        if (player.input.jumping) {
            player.addVelocity(0, speed, 0);
        }
        if (player.input.sneaking) {
            player.addVelocity(0, -speed, 0);
        }

        double velocity = Math.sqrt(player.getVelocity().x * player.getVelocity().x + player.getVelocity().z * player.getVelocity().z);
        if (velocity > maxSpeed) {
            player.setVelocity(player.getVelocity().normalize().multiply(maxSpeed));
        }
    }

    private static boolean isMovingForwardWithVelocity(ClientPlayerEntity player) {
        Vec3d velocity = player.getVelocity();
        double velocityLength = velocity.length();
        Vec3d lookDirection = player.getRotationVec(1.0F);
        Vec3d normalizedVelocity = velocity.normalize();
        boolean isMovingForward = normalizedVelocity.dotProduct(lookDirection) > 0.99;
        boolean hasCorrectVelocity = Math.abs(velocityLength - speed) < 0.001;

        return isMovingForward && hasCorrectVelocity;
    }
}
