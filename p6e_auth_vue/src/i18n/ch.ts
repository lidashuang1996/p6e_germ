export default {
  index: {
    // 主页的 url 地址
    url: 'https://baidu.com',
    // 头部
    header: {

    },
    // 内容
    content: {
      empty: {
        status: 'warning',
        button: '返回主页',
        title: '数据解析出现异常'
      }
    },
    // 底部
    footer: [
      [
        {
          url: 'javascript:void(0);',
          target: '_blank',
          name: 'AAA'
        },
        {
          url: 'javascript:void(0);',
          target: '_blank',
          name: 'BBB'
        },
        {
          url: 'javascript:void(0);',
          target: '_blank',
          name: 'CCC'
        },
        {
          url: 'javascript:void(0);',
          target: '_blank',
          name: 'DDDDDDD'
        },
        {
          url: 'javascript:void(0);',
          target: '_blank',
          name: 'FFFFFFF'
        },
        {
          url: 'javascript:void(0);',
          target: '_blank',
          name: 'GGGGGGG'
        },
        {
          url: 'javascript:void(0);',
          target: '_blank',
          name: 'JJJJJJJ'
        }
      ],
      [
        {
          url: 'javascript:void(0);',
          target: '_blank',
          name: '© 2021 XXXX'
        },
        {
          url: 'javascript:void(0);',
          target: '_blank',
          name: '京 ICP 证 XXXX 号'
        },
        {
          url: 'javascript:void(0);',
          target: '_blank',
          name: '京公网安备 XXXXX 号'
        },
        {
          url: 'javascript:void(0);',
          target: '_blank',
          name: 'XXXXXXXXXXXXXXXX'
        },
        {
          url: 'javascript:void(0);',
          target: '_blank',
          name: '互联XXXXXXXXXXXXXXXXXXXX格证书（京）- XXXXXXXX - 2017 - 0067'
        }
      ],
      [
        {
          url: 'javascript:void(0);',
          target: '_blank',
          name: '侵权举报'
        },
        {
          url: 'javascript:void(0);',
          target: '_blank',
          name: '网上有害信息举报专区'
        },
        {
          url: 'javascript:void(0);',
          target: '_blank',
          name: '儿童色情信息举报专区'
        },
        {
          url: 'javascript:void(0);',
          target: '_blank',
          name: '违法和不良信息举报：010-82716601'
        }
      ]
    ]
  },
  sign: {
    in: {
      ap: {
        title: '账号登录'
      },
      qc: {
        title: '扫码登录',
        subtitle: '打开 APP 点开扫一扫，即可完成登录',
        expiration: ['二维码已经过期', '请点击重新获取']
      }
    },
    up: {
    },
    other: {
      title: '社交账号登录',
      list: [
        {
          type: 'WC',
          name: '微信'
        },
        {
          type: 'QQ',
          name: 'QQ'
        },
        {
          type: 'SA',
          name: '微博'
        }
      ]
    }
  },
  error: {
    SERVICE_EXCEPTION: {
      title: '错误',
      okText: '确定',
      content: '服务器数据异常，请稍后重试',
      describe: '服务器数据异常'
    },
    SERVICE_NOT_ENABLE: {
      title: '抱歉',
      okText: '确定',
      content: '第三方平台「{platform}登录」暂未开通',
      describe: '服务未启用'
    }
  }
};
