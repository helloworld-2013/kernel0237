var path = require("path");
module.exports = {
	entry: {
		app: ["./frontend/app/main.js"]
	},
	output: {
		path: path.resolve(__dirname, "../frontend/dist"),
		publicPath: "/assets/js/",
		filename: "app.bundle.js"
	}
};