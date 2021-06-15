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
        title: '账号登录',
        np: {
          title: '免密码登录',
          account: {
            placeholder: '请输入邮箱/手机号码',
            errors: [
              '请输入账号',
              '账号格式不正确'
            ]
          },
          code: {
            placeholder: '请输入验证码',
            button: {
              obtain: '获取验证码',
              countdown: '{data} 秒后可重发'
            },
            errors: [
              '请输入验证码',
              '请输入完整的验证码'
            ]
          },
          button: '登录/注册'
        },
        yp: {
          title: '密码登录',
          button: '登 录',
          buttonForgetPassword: '忘记密码？',
          account: {
            placeholder: '请输入邮箱/手机号码',
            errors: [
              '请输入账号',
              '账号格式不正确'
            ]
          },
          password: {
            placeholder: '请输入密码',
            errors: [
              '请输入密码',
              '密码格式不正确'
            ]
          }
        },
        agreement: [
          {
            type: 'text',
            content: '未注册账号验证后自动登录，注册即代表同意'
          },
          {
            type: 'link',
            content: '《XXXX协议》',
            url: 'https://www.baidu.com'
          },
          {
            type: 'link',
            content: '《隐私保护指引》',
            url: 'https://www.baidu.com'
          }
        ]
      },
      qc: {
        title: '扫码登录',
        subtitle: '打开 APP 点开扫一扫，即可完成登录',
        expiration: ['二维码已经过期', '请点击重新获取']
      }
    },
    up: {
    },
    notice: {
      button: '确 认',
      content: [
        '你正在进行授权给',
        '确认代表同意相关协议。'
      ]
    },
    fp: {
      title: '忘记密码',
      step: [
        {
          button: '下一步',
          content: [
            ['可以通过{mode}的方式找回密码！', { mode: { name: '「人工申诉」', url: 'https://baidu.com' } }],
            ['可以通过注册的邮箱/手机号码，接收验证码，通过认证找回忘记的密码！']
          ],
          account: {
            placeholder: '请输入邮箱/手机号码',
            errors: [
              '请输入账号',
              '账号格式不正确'
            ]
          },
          code: {
            placeholder: '请输入验证码',
            button: {
              obtain: '获取验证码',
              countdown: '{data} 秒后可重发'
            },
            errors: [
              '请输入验证码',
              '请输入完整的验证码'
            ]
          }
        },
        {
          button: '确 定',
          content: [
            ['请重新设置您账号（邮箱/手机号码）的密码，重新设置后可以使用账号（邮箱/手机号码）密码登录的方式登录该账号。']
          ],
          password1: {
            placeholder: '请输入密码',
            errors: [
              '请输入密码',
              '密码格式不正确'
            ]
          },
          password2: {
            placeholder: '请输入确认密码',
            errors: [
              '请输入密码',
              '密码格式不正确'
            ]
          }
        }
      ]
    },
    ba: {
      title: '绑定账号',
      content: [
        ['使用第三方平台初次登录，需要绑定一下账号（手机号/邮箱），绑定账号后可以通过账号密码登录或者第三方平台登录。']
      ],
      button: '确 定',
      account: {
        placeholder: '请输入邮箱/手机号码',
        errors: [
          '请输入账号',
          '账号格式不正确'
        ]
      },
      code: {
        placeholder: '请输入验证码',
        button: {
          obtain: '获取验证码',
          countdown: '{data} 秒后可重发'
        },
        errors: [
          '请输入验证码',
          '请输入完整的验证码'
        ]
      }
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
    PAGE_EXPIRED: {
      title: '错误',
      okText: '确定',
      content: '页面过期，请刷新重试',
      describe: '页面过期异常'
    },
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
