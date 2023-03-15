export async function getDataFrom(endPoint, callback) {
  const fetchResult = async () => {
    const response = await fetch(endPoint);
    return response;
  };

  const result = await fetchResult();
  let data = "";

  if (result.status < 300) {
    data = await result.json();
  } else {
    // window.alert("Fetch " + result.status);
    data = {
      message: "Operacija nepavyko!",
      status: result.status,
      ok: result.ok,
      statusText: result.statusText,
    };
  }

  callback(data);

  return data;
}

// export async function postDataTo(teacher, address, type) {
//   const fetchResult = async () => {
//     // console.log(teacher);
//     const response = await fetch(address, {
//       method: type,
//       headers: { "Content-Type": "application/json" },
//       body: JSON.stringify(teacher),
//     });

//     return response;
//   };

//   const result = await fetchResult();
//   console.log(result);
//   let data = await result.json();
//   return data;
// }

export async function postDataTo(teacher, address, type) {
     const response = await fetch(address, {
      method: type,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(teacher),
    });

    // console.log(response);

  // const result = await fetchResult();
  // console.log(result);
  // let data = await result.json();
  return response;
}


export async function switchActive(id) {
 
    const response = await fetch(
      `/api/v1/teachers/active?tid=${id}&active=false`,
      {
        method: "PATCH",
        headers: { "Content-Type": "application/json" },
      }
    );
    
     console.log(`Response status: ${response.status}`);
  
    return response;
}