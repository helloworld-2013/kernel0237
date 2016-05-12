var helpers = require('./helpers');
var oauth = require('./oauth');

var gitConfig = {
	githubClient: oauth.GITHUB_CLIENT_ID,
	githubSecret: oauth.GITHUB_CLIENT_KEY,
	baseURL: `http://${helpers.host}:${helpers.port}`,
	loginURI: '/login',
	callbackURI: '/callback',
	scope: 'user' // optional, default scope is set to user
};

module.exports = gitConfig;