<template>
  <div class="me">
    <div class="title">
      <span v-text="$t('sign.me.title')"></span>
    </div>
    <div class="content">
      <ul class="table">
        <template :key="'ME_TABLE_ROW_' + index"
                  v-for="(item, index) in $tm('sign.me.content')">
          <li class="row">
            <ul>
              <li><span v-text="item.name"></span></li>
              <li><span v-text="getUserData(item.key)"></span></li>
            </ul>
          </li>
        </template>
      </ul>
    </div>
    <div class="button">
      <a-button type="danger" block @click.stop="logout">
        <span v-text="$t('sign.me.button')"></span>
      </a-button>
    </div>
  </div>
</template>

<script lang="ts">
import { Modal } from 'ant-design-vue';
import { ExclamationCircleOutlined } from '@ant-design/icons-vue';
import { Options, Vue } from 'vue-class-component';
import { createVNode } from 'vue';

@Options({})
export default class HelloWorld extends Vue {

  private getUserData (n: string) {
    return '4444';
  }

  private logout () {
    const ree = this.$tm('sign.me.logout') as { [ key: string ]: string };
    Modal.confirm({
      centered: true,
      title: ree.title,
      icon: createVNode(ExclamationCircleOutlined),
      okText: ree.okText,
      cancelText: ree.cancelText,
      onOk () {
        console.log('OK');
      }
    });
  }
}
</script>
