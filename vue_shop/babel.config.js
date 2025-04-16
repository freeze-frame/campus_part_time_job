// 这是项目发布阶段需要用到的babel插件
const prodPlugins = []
// 通过process.env.NODE_ENV项目状态来进行添加插件
if (process.env.NODE_ENV === 'production') {
  prodPlugins.push('transform-remove-console')
}

module.exports = {
  presets: ['@vue/cli-plugin-babel/preset'],
  plugins: [
    [
      'component',
      {
        libraryName: 'element-ui',
        styleLibraryName: 'theme-chalk'
      }
    ],
    // 发布时用的插件数组
    ...prodPlugins,
    '@babel/plugin-syntax-dynamic-import',
    "@babel/plugin-transform-private-methods",
    "@babel/plugin-proposal-private-methods"
  ]
}
