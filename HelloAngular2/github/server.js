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

app.use(express.static(__dirname + '/public'));
app.use('/assets', express.static(__dirname + '/frontend/dist'));

app.get('/login',function(req,res) {
	return githubOAuth.login(req, res)
});

app.get('/callback',function(req,res) {
	return githubOAuth.callback(req, res)
});

app.get('/following',function(req,res) {
	github.me.following.read()
	.then(function(users){
		res.setHeader('Content-Type', 'application/json');
		res.send(users);
	});
});

app.get('/following/:userid',function(req,res) {
	github.users(req.params.userid).following.read()
	.then(function(users){
		res.setHeader('Content-Type', 'application/json');
		res.send(users);
	});
});

var server = app.listen(8888, () => {
	let host = server.address().address;
	let port = server.address().port;

	host = (host === '::' ? 'localhost' : host);
	console.log(`listening at http:${host}:${port}`);
});

githubOAuth.on('error', function(err) {
	console.error('there was a login error', err)
})

githubOAuth.on('token', function(token, serverResponse) {
	github = new octokat({
		token: token.access_token
	});
	serverResponse.sendFile(__dirname + '/public/index.html');
})
