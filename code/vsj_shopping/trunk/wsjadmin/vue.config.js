module.exports = {
    publicPath: './',
    assetsDir: 'public',
    productionSourceMap: false,
    devServer: {
        proxy: {
            '/api/user_app_h5': {
                target: "http://172.16.0.231:8751/api/v1/"

            }
        }
       
    }
}