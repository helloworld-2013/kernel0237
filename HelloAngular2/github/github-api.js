import octokat from 'octokat';
import oauth from 'github-oauth';
import gitconfig from './config/gitconfig';
import helpers from './config/helpers';

export class GithubAPI {
	constructor() {
		this.github = {};
		this.githubOAuth = oauth(gitconfig);
		this.githubOAuth.on('error', (err) => {
			console.error('there was a login error', err);
		});
		this.githubOAuth.on('token', (token, serverResponse) => {
			this.github = new octokat({
				token: token.access_token
			});
			serverResponse.sendFile(__dirname + '/public/index.html');
		});
	}

	login(req,res) {
		return this.githubOAuth.login(req, res);
	}

	callback(req,res) {
		return this.githubOAuth.callback(req, res);
	}

	following(userid,res) {
		if (userid !== '-') {
			this.github.users(userid).following.read()
			.then((result) => {
				this.generateResult(result,this.github.users(userid),res);
			});
		} else {
			this.github.me.following.read()
			.then((result) => {
				this.generateResult(result,this.github.me,res);
			});
		}
	}

	generateResult(result,user,res) {
		user.read().then((userObj) => {
			var results = {"nodes": [], "links": []};
			var json = JSON.parse(result);
			results.nodes.push(JSON.parse(userObj));
			results.nodes.push(json);

			for (var i = 0;i<json.length;i++) {
				results.links.push({"source": 0, "target": (i+1)});
			}

			res.setHeader('Content-Type', 'application/json');
			res.send(results);
		});
	}
}