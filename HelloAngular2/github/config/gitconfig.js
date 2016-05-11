var helpers = require('./helpers');

var gitConfig = {
	githubClient: process.env.GITHUB_CLIENT_ID,
	githubSecret: process.env.GITHUB_CLIENT_KEY,
	baseURL: `http://${helpers.host}:${helpers.port}`,
	loginURI: '/login',
	callbackURI: '/callback',
	scope: 'user' // optional, default scope is set to user
};

module.exports = gitConfig;