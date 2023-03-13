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

export async function postDataTo(teacher, address, type) {
  const fetchResult = async () => {
    console.log(teacher);
    const response = await fetch(address, {
      method: type,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(teacher),
    });

    return response;
  };

  const result = await fetchResult();
  let data = await result.json();
  return data;
}

export async function switchActive(id) {
  console.log(id);
  const response = await fetch(
    `/api/v1/teachers/active?tid=${id}&active=false`,
    {
      method: "PATCH",
      headers: { "Content-Type": "application/json" },
    }
  );

  const body = await response.json();
  const headers = response.headers;
  const status = response.status;
  // console.log(response);
  return { response };
}
