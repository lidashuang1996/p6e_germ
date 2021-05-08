<template>
  <div class="error error-404">
    <h1 class="title">404</h1>
    <p class="subtitle">很抱歉，您访问的页面不存在！</p>
    <div class="">
      <video id="videoElement" width="300px" height="200px"></video>
      <el-button @click="play">播放</el-button>
    </div>
  </div>
</template>

<script lang="ts">
import flvjs from 'flv.js';
import { Options, Vue } from 'vue-class-component';

@Options({})
export default class Error404 extends Vue {
  private flvPlayer: any = null;
  public mounted () {
    if (flvjs.isSupported()) {
      const videoElement = document.getElementById('videoElement');
      this.flvPlayer = flvjs.createPlayer({
        type: 'flv',
        url: 'http://1011.hlsplay.aodianyun.com/demo/game.flv'
      });
      this.flvPlayer.attachMediaElement(videoElement);
      this.flvPlayer.load();
    }
  }

  public play () {
    this.flvPlayer.play();
  }
}
</script>
<style>
  #videoElement {
    width: 820px;
    height: 400px;
  }
</style>
