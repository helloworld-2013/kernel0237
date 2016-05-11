var githubOAuth = require('github-oauth')({
	githubClient: process.env.GITHUB_CLIENT_ID,
	githubSecret: process.env.GITHUB_CLIENT_KEY,
	baseURL: 'http://localhost:8888',
	loginURI: '/login',
	callbackURI: '/callback',
	scope: 'user' // optional, default scope is set to user
});

var express = require('express'),
	app = express(),
	octokat = require('octokat'),
	github = {};

app.use(express.static(__dirname + '/static'));

app.get('/login',function(req,res) {
	return githubOAuth.login(req, res)
});

app.get('/callback',function(req,res) {
	return githubOAuth.callback(req, res)
});

app.get('/following/:userid',function(req,res) {
	github.users(req.params.userid).following.read()
	.then(function(users){
		res.setHeader('Content-Type', 'application/json');
		res.send(users);
	});
});

app.listen(8888);

githubOAuth.on('error', function(err) {
	console.error('there was a login error', err)
})

githubOAuth.on('token', function(token, serverResponse) {
	github = new octokat({
		token: token.access_token
	});

	github.me.following.read()
	.then(function(users){
		var _users = JSON.parse(users)
		console.log('I am following below user(s):');
		for (var i = 0;i < _users.length;i++) {
			console.log(_users[i].login);
		}
	});

	serverResponse.sendFile(__dirname + '/static/index.html');
})
