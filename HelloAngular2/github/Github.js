var githubOAuth = require('github-oauth')({
	githubClient: 'a56f003b74129a65180c',
	githubSecret: '2b2e40b66a443548be4f3eb107afa122514daaf5',
	baseURL: 'http://localhost:8888',
	loginURI: '/login',
	callbackURI: '/callback',
	scope: 'user' // optional, default scope is set to user
});

var express = require('express'),
	app = express(),
	userToken = '';

app.use(express.static(__dirname + '/static'));

app.get(['/login'],function(req,res) {
	return githubOAuth.login(req, res)
});

app.get('/callback',function(req,res) {
	return githubOAuth.callback(req, res)
});

app.listen(8888);

githubOAuth.on('error', function(err) {
	console.error('there was a login error', err)
})

githubOAuth.on('token', function(token, serverResponse) {
	userToken = token.access_token
	serverResponse.sendFile(__dirname + '/static/index.html');
})
