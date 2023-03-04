app.controller("order-ctrl",function($scope,$http){
    $scope.items = [];
    $scope.form = {};
    $scope.acc = [];

    $scope.initialize = function(){
        //load orders
        $http.get("/rest/orders").then(resp=>{
            $scope.items = resp.data;
            $scope.items.forEach(item=>{
                item.createDate = new Date(item.createDate);
            })
        })
        //load accounts
        $http.get("/rest/accounts").then(resp=>{
            $scope.acc = resp.data;
        })
    }

    //Xoá form
    $scope.reset = function(){
		$scope.form = {
			createDate:new Date(),
		}
    }

    //Hiển thị lên form
    $scope.edit = function(item){
		$scope.form = angular.copy(item);
		$('#pills-home-tab').tab('show');
    }

    //Update sản phẩm
    $scope.update = function(){
		var item = angular.copy($scope.form);
		$http.put(`/rest/orders/${item.id}`,item).then(resp=>{
			var index = $scope.items.findIndex(p=>p.id == item.id);
			$scope.items[index] = item;
			alert('Cập nhật đơn hàng thành công!');
			console.log(resp.data);
		}).catch(err=>{
			alert('Lỗi cập nhật đơn hàng!')
			console.log("Error ",err);
		})
	}
    
    $scope.pager = {
		page:0,
		size:10,
		get items(){
			var start = this.page * this.size;
			return	$scope.items.slice(start,start+this.size);
		},
		get count(){
			return Math.ceil(1.0*$scope.items.length/this.size);
		},
		first(){
			this.page = 0;
		},
		previous(){
			this.page--;
			if(this.page<0){
				this.last();
			};
		},
		next(){
			this.page++;
			if(this.page >= this.count){
				this.first();
			};
		},
		last(){
			this.page = this.count -1;
		},
	}
	
	//Khởi đầu
    $scope.initialize();
	$scope.reset();
})