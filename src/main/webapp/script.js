var storePath = "http://localhost:8989/candy-shop/store";

async function showProducts() {

  var response = await fetch(storePath, {method: "GET"});
  var resp_body = await response.json();
  var names = resp_body.productNames;

  var tableBody = document.getElementById("body");
  for(var name of names){
      var article = document.createElement('article');
      article.className = 'item';

      var paragraph = document.createElement("p");
      paragraph.className = 'product';
      paragraph.textContent = name;
      var button = document.createElement("button");
      button.className = "purchaser";
      button.textContent = "Purchase";
      button.addEventListener('click', function(event) {
        openPopup(event);
      });

      article.appendChild(paragraph);
      article.appendChild(button);

      var row = document.createElement('tr');
      var cell = document.createElement('td');
      cell.appendChild(article);
      row.appendChild(cell);

      tableBody.appendChild(row);
  }
}

showProducts();

var productPath = "http://localhost:8989/candy-shop/store/product";


async function writeProdInfo(param_name){

    var response = await fetch(productPath + "?name=" + param_name, {method: "GET"});
    var getProductInfoResponse = await response.json();

    var name = getProductInfoResponse.name;
    var price = getProductInfoResponse.price;
    var amount = getProductInfoResponse.amount;

    var prod_name = document.getElementById("prod_name");
    var prod_price = document.getElementById("price");
    var prod_amount = document.getElementById("available");

    prod_name.textContent = name;
    prod_price.textContent = price;
    prod_amount.textContent = amount;
}

function openPopup(event) {
  var clickedButton = event.target;
  var article = clickedButton.parentNode;
  var paragraph = article.getElementsByClassName("product")[0];
  var name = paragraph.textContent;

  var popup = document.getElementById("prod_info");
  popup.style.display = "block";

  writeProdInfo(name);
}


function closePopup() {
  document.getElementById("integerInput").value = null;
  document.getElementById("prod_info").style.display = "none";
}

function getProdName() {
 return document.getElementById("prod_name").textContent;
}

function getAmount() {
  return document.getElementById("integerInput").value;
}









async function buy(){
  var name = getProdName();
  var amount = getAmount();

  var purchaseRequest = {
      name: name,
      amount: amount
  };
  var req_body = JSON.stringify(purchaseRequest);

  var response = await fetch(productPath, {
      method: "POST",
      body: req_body
  });

  if(response.ok){
    closePopup();
  }
  else{
    alert("Your purchase has failed!");
  }

}