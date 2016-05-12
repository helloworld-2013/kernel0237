import express from 'express';
import octokat from 'octokat';
import gitconfig from './config/gitconfig';
import helpers from './config/helpers';
import oauth from 'github-oauth';

let githubOAuth = oauth(gitconfig);
let app = express();
let github = {};
let server = null;

app.use(express.static(__dirname + '/public'));
app.use('/assets', express.static(__dirname + '/frontend/dist'));
app.get('/login', (req,res) => githubOAuth.login(req, res));

app.get('/callback', (req, res) => githubOAuth.callback(req, res));
app.get('/following', (req,res) => {
	github.me.following.read()
		.then((users) => {
			res.setHeader('Content-Type', 'application/json');
			res.send(users);
		});
});

app.get('/following/:userid', (req, res) => {
	github.users(req.params.userid).following.read()
		.then((users) => {
			res.setHeader('Content-Type', 'application/json');
			res.send(users);
		});
});

server = app.listen(helpers.port, () => {
	let host = server.address().address;
	let port = server.address().port;

	host = (host === '::' ? helpers.host : host);
	
	console.log('-------------------------------------------------');
	console.log(`listening at http:${host}:${port}`);
});

githubOAuth.on('error', (err) => {
	console.error('there was a login error', err);
});

githubOAuth.on('token', (token, serverResponse) => {
	github = new octokat({
		token: token.access_token
	});
	serverResponse.sendFile(__dirname + '/public/index.html');
});