var app = angular.module('app', ['ngAnimate', 'ngAria', 'ngMessages', 'ui.router', 'ngMaterial', 'md.data.table']);

angular.module('app')
	.config(function($stateProvider, $urlRouterProvider) {
		$urlRouterProvider.otherwise('/');
		
		$stateProvider
			.state('home', {
				url: '/',
				templateUrl: 'views/home/index.html',
				controller: 'HomeCtrl'
			})
			.state('login', {
				url: '/login',
				templateUrl: 'views/home/login.html',
				controller: 'LoginCtrl'
			})
			.state('trackStatus', {
				url: '/track-status',
				templateUrl: 'views/home/track-status.html',
				controller: 'TrackStatusCtrl'
			});
	});