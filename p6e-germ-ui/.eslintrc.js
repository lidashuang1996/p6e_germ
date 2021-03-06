module.exports = {
  root: true,
  env: {
    node: true
  },
  extends: [
    'plugin:vue/essential',
    '@vue/standard',
    '@vue/typescript/recommended'
  ],
  parserOptions: {
    ecmaVersion: 2020
  },
  rules: {
    "@typescript-eslint/explicit-module-boundary-types": "off",
    'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    semi: ['error', 'always'],
    'space-before-function-paren': ['error', 'always'],
    'no-empty-function': ['warn', { allow: ['functions'] }]
  },
  globals: {
    TableView: true,
    ModalView: true,
    HttpMessageGroupListParam: true,
    HttpMessageGroupListResult: true,

    HttpMessageGroupPlatformListParam: true,
    HttpMessageGroupPlatformListResult: true,

    HttpMessageLogListParam: true,
    HttpMessageLogListDataResult: true,
    HttpMessageLogListItemDataResult: true,
    HttpMessageLogListItemFlowDataResult: true,
    HttpMessageGroupPlatformListItemDataResult: true
  },
  overrides: [
    {
      files: [
        '**/__tests__/*.{j,t}s?(x)',
        '**/tests/unit/**/*.spec.{j,t}s?(x)'
      ],
      env: {
        jest: true
      }
    }
  ]
};
