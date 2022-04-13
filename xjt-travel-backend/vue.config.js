const path = require('path');

function resolve(dir) {
	return path.join(__dirname, dir)
}

module.exports = {
	devServer: {  //配置开发环境
		host: 'localhost', //端口号
		open: true,
		port: 8001,
		proxy: {
			'^/api': {
				target: 'http://localhost:8880',
				ws: false,
				changOrigin: true,      //允许跨域
				pathRewrite: {
					'^/api': ''
				}
			}
		}
	},
	configureWebpack: {
		// provide the app's title in webpack's name field, so that
		// it can be accessed in index.html to inject the correct title.
		name: "XG_Travel",
		resolve: {
			alias: {
				'@': resolve('src'),
			}
		}
	},
	chainWebpack(config) {
		// set svg-sprite-loader
		config.module
				.rule('svg')
				.exclude.add(resolve('src/assets/icons/svg'))
				.end()
		config.module
				.rule('icons')
				.test(/\.svg$/)
				.include.add(resolve('src/assets/icons/svg'))
				.end()
				.use('svg-sprite-loader')
				.loader('svg-sprite-loader')
				.options({
					symbolId: 'icon-[name]',
					plugins: [
						{removeAttrs: {attrs: 'path:fill'}}
					]
				})
				.end()
	}
}
