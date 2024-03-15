function getValue(id){
  return document.getElementById(id).value;
}
function getElement(id){
  return document.getElementById(id);
}
function showWarning(id){
  getElement(id).style.display = "block";
}
function hideWarnings(){
  getElement("name_warning").style.display = "none";
  getElement("amount_warning").style.display = "none";
  getElement("password_warning").style.display = "none";
}


function require(){
  hideWarnings();

  var prod_name = getValue("name");
  var prod_amount = getValue("amount");
  var prod_password = getValue("password");
  var allGood = true;

  if(prod_name == "") {
    showWarning("name_warning");
    allGood = false;
  }
  if(prod_amount == "") {
    showWarning("amount_warning");
    allGood = false;
  }
  if(prod_password == "") {
    showWarning("password_warning");
    allGood = false;
  }
  return allGood;
}

async function refill(){
  var productPath = "http://localhost:8989/candy-shop/store/product";
  if(!require()) return;
  var addProductRequest = {
      password: getValue("password"),
      name: getValue("name"),
      amount: getValue("amount")
  };
  var req_body = JSON.stringify(addProductRequest);
  var response = await fetch(productPath, {
      method: "PUT",
      body: req_body
  });
  alert(response.status);
}