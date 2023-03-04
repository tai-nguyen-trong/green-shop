const app = angular.module("shopping-cart-app",[]);
app.controller("shopping-cart-ctrl",function($scope,$http){
    /*
    * QUẢN LÝ GIỎ HÀNG
    */
//   Định nghĩa 1 đối tượng nằm trong scope của angular
   $scope.cart={
       items:[],

       //Thêm sản phẩm vào giỏ hàng, nút thêm vào giỏ hàng
       add(id){
           var item = this.items.find(item=>item.id == id); //dựa vào id để kiểm tra đã có mặt hàng đó trong giỏ chưa
			//Nếu có mặt hàng đó(Cùng id), thì tăng số lượng lên 1 và lưu vào LocalStorage
           if(item){
				
               item.qty++;
			   alert("Đã thêm sản phẩm vào giỏ hàng!");
               this.saveToLocalStorage();
           }else{
				// chưa có thì tải sản phẩm trên server về thông qua 1 API: rest/products/${id}. rest.controller
               $http.get(`/rest/products/${id}`).then(resp=>{
					//sau khi tải về là số lượng bằng 1
                   resp.data.qty=1;
                   this.items.push(resp.data);
                   this.saveToLocalStorage();
               })
           }
       },

       //Xoá sản phẩm khỏi giỏ hàng
       remove(id){
		//findIndex:Tìm vị trí sản phẩm nằm trong giỏ hàng qua id đó, splice:xóa phần tử khỏi mảng
           var index = this.items.findIndex(item=>item.id == id);
           this.items.splice(index, 1);
			alert("Xóa sản phẩm thành công!");
           this.saveToLocalStorage();
       },

       //Xoá sạch các mặt hàng trong giỏ
       clear(){
			//cho 1 mảng rỗng và lưu vào LocalStorage()
           this.items = [];
			alert("Xóa sạch sản phẩm thành công!");
           this.saveToLocalStorage();
       },
       
       //Tính thành tiền của một sản phẩm
       amt_of(item){},



       //Tính tổng số lượng các mặt hàng trong giỏ
		//duyệt qua các mặt hàng sau đó lấy số lượng  
       get count(){
        return this.items
            .map(item=>item.qty)  //lấy số lượng
            .reduce((total,qty)=> total+=qty,0);  //tính tổng
       },

       //Tổng thành tiền các mặt hàng trong giỏ
       get amount(){
        return this.items
            .map(item=>item.qty * item.price)   //tổng số lượng * đơn giá
            .reduce((total,qty)=> total+=qty,0);  
       },



       //Lưu giỏ hàng vào local storage
       saveToLocalStorage(){
           //dùng angular để copy xong đổi các mặt hàng sang json
           var json = JSON.stringify(angular.copy(this.items));
			//Dùng chuỗi json trên lưu vào localStorage với tên là cart
           localStorage.setItem("cart",json);
       },





       //Đọc giỏ hàng từ local storage
       loadFromLocalStorage(){
			//Đọc lại cart từ trong localStorage ra
           var json = localStorage.getItem("cart");
			// Nếu có thì chuyển qua Json và gán dô items, không thì gán mảng rỗng
           this.items = json?JSON.parse(json) : [];
       },


   };
	//Khi ứng dụng bắt đầu, ta nên tải lại toàn bộ các mặt hàng đã lưu trong LocalStorage vào trong cart
   $scope.cart.loadFromLocalStorage();


	
	
	
	//Đặt hàng và quản lí đơn hàng, hiển thị ngày đặt hàng bên form thanh toán 
   $scope.order = {
		//ngày hiện tại
       createDate:new Date(),
		//địa chỉ đặt hàng, trống để người dùng nhập
       //address:"",
		//để đặt hàng thì ta cần chuyển lên thông tin người đăng nhập, 
		//user ở trên giao diện, dùng id="username" = #username(tương đương trong file angular) trong file checkout.html để lấy tên 
       account:{username:$("#username").text()},
		//lấy toàn bộ mặt hàng bên trong giỏ hàng để gửi lên server, vì là 1 thuộc tính nên dùng get
       get orderDetails(){
           return $scope.cart.items.map(item=>{
               return{
                   product:{id:item.id},
                   price:item.price,
                   quantity:item.qty
               }
           })
       },
		//gửi đơn hàng lên server để tạo đơn hàng trong csdl
       purchase(){
           var order = angular.copy(this);
           //Thực hiện đặt hàng
           $http.post("/rest/orders",order).then(resp=>{  ///rest/orders bên OrderRestController
               alert("Đặt hàng thành công!");
				//đặt hàng thành công thì xóa giỏ hàng
               $scope.cart.clear();
				//Chuyển đến trang chi tiết đơn hàng với mã đơn hàng vừa đặt dc(/order/detail laf file OrderController)
               location.href = "/order/detail/"+resp.data.id;
           }).catch(err=>{
               alert("Sai hoặc thiếu thông tin mua hàng!")
               console.log(err);
           })
       }
   };





	// lấy thông tin user
	function load_user(){
		var username =  $("#username").text()
		$http.get(`/rest/account/${username}`).then(resp => {
			$scope.order.phoneNumber = resp.data.phoneNumber;
			$scope.order.address = resp.data.address;
		}).catch(error => {
			 
			console.log(error);
		});
	}
	
	load_user();
	


})