import express from 'express';
import {GithubAPI} from './github-api';
import helpers from './config/helpers';

let app = express();
let server = null;
let github = new GithubAPI();

app.use(express.static(__dirname + '/public'));
app.use('/assets', express.static(__dirname + '/frontend/dist'));

app.get('/login', (req,res) => github.login(req, res));
app.get('/callback', (req, res) => github.callback(req, res));
app.get('/following/:userid', (req, res) => github.following(req.params.userid,res));

server = app.listen(helpers.port, () => {
	let host = server.address().address;
	let port = server.address().port;

	host = (host === '::' ? helpers.host : host);
	
	console.log('-------------------------------------------------');
	console.log(`listening at http:${host}:${port}`);
});