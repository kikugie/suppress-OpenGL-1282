package io.github.adamraichu.suppressopengl1280.mixin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gl.GlDebug;

@Mixin(GlDebug.class)
public abstract class GlDebugMixin {
  private static boolean hasPostedMessage = false;

  private static final Logger LOGGER = LoggerFactory.getLogger("Suppress OpenGL Error 1282");

  @Inject(at = @At(value = "HEAD"), method = "info(IIIIIJJ)V", cancellable = true)
  private static void suppressMessage(int source, int type, int id, int severity, int messageLength, long message,
      long l,
      CallbackInfo ci) {
    if (!(id == 1282)) {
      return;
    }
    if (hasPostedMessage) {
      ci.cancel();
    } else {
      LOGGER.info("OpenGL error 1282 (GL error GL_INVALID_OPERATION) has been thrown.");
      LOGGER.info("Suppressing following error logs.");
      hasPostedMessage = true;
    }
  }
}