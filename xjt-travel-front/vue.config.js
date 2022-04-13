module.exports = {
	devServer:{  //配置开发环境
		host: 'localhost', //端口号
		open:true,
		port: 8002,
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
	}
};
