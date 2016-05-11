var webpack = require("webpack");

module.exports = {
	entry: {
		vendor: ["./frontend/app/vendor"],
		app: ["./frontend/app/main"]
	},
	output: {
		path: __dirname,
		filename: "../frontend/dist/[name].bundle.js"
	},
	resolve: {
		extensions: ['', '.js', '.ts']
	},
	devtool: 'source-map',
	module: {
		loaders: [
		{
			test: /\.ts/,
			loaders: ['ts-loader'],
			exclude: /node_modules/
		}
		]
	}
};