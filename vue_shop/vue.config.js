const { defineConfig } = require('@vue/cli-service')
const path = require("path")


module.exports = defineConfig({
  devServer: {
    host: '0.0.0.0',
    // https:true,
    port: 6103,
    client: {
      webSocketURL: 'ws://0.0.0.0:6103/ws',

    },
    headers: {
      'Access-Control-Allow-Origin': '*',
    }
  },

  transpileDependencies: true,
  transpileDependencies: true,
  lintOnSave: false,
  configureWebpack:{
    resolve:{
      alias:{
        "@":path.resolve(__dirname,"src"),
        'pdfjs-dist/build/pdf.worker.entry': path.resolve(__dirname, 'D:\\javadata\\campus_part_time_job\\vue_shop\\src\\assets\\js\\pdf.worker.mjs')
      }
    },
    devServer:{
      proxy:{
        "/api":{
          target:"http://127.0.0.1:8888/"
        }
      }
    }
  },

  lintOnSave: false,
  publicPath: './',
  transpileDependencies: true,
  // config.when 第一项判断产品的模式，然后在第二项设置打包的入口文件
  chainWebpack: (config) => {
    // 发布模式
    config.when(process.env.NODE_ENV === 'production', (config) => {
      config.entry('app').clear().add('./src/main-prod.js')
      // 对依赖性优化检查window环境是否已经存在变量
      config.set('externals', {
        vue: 'Vue',
        'vue-router': 'VueRouter',
        axios: 'axios',
        echarts: 'echarts',
        nprogress: 'NProgress'
        // 'vue-quill-editor': 'VueQuillEditor'
      })

      // 设置参数，用于index.html判断开发还是发布模式，来引入cdn
      config.plugin('html').tap((args) => {
        args[0].isProd = true
        return args
      })
    })

    // 开发模式
    config.when(process.env.NODE_ENV === 'development', (config) => {
      config.entry('app').clear().add('./src/main-dev.js')

      config.plugin('html').tap((args) => {
        args[0].isProd = false
        return args
      })
    })
  }
})
