<template>
  <!-- Qr Code Component -->
  <div class="qr-code-component">
    <!-- canvas -->
    <canvas ref="refQrCodeCanvas"></canvas>
  </div>
</template>

<script lang="ts">
import QrCode from 'qrcode';
import { Options, Vue } from 'vue-class-component';
import { QrCodeComponentInterface } from '../components';

@Options({})
export default class QrCodeComponent extends Vue implements QrCodeComponentInterface {
  /** canvas */
  public canvas: HTMLCanvasElement | null = null;

  /** qr code */
  public qrCode (text: string) {
    if (this.canvas === null) {
      this.canvas = this.$refs.refQrCodeCanvas as HTMLCanvasElement;
    }
    QrCode.toCanvas(this.canvas, text, {
      width: 220,
      scale: 4,
      color: {
        dark: '#226AF6',
        light: '#ffffff'
      }
    });
  }
}
</script>
