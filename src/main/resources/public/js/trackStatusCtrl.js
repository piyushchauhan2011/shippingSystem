angular.module('app')
	.controller('TrackStatusCtrl', function($scope, $http) {
		$scope.trackStatus = function() {
			console.log($scope.trackingNumber);
		}
	});