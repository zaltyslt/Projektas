export async function getDataFrom(endPoint, callback) {
  const fetchResult = async () => {
    const response = await fetch(endPoint);
    //   .then((response) => response.json())
    //   .then((data) => {
    //     callback(data); // invoke callback with data
    //     return data;
    //   });
    const body = await response.json();
    const headers = response.headers;
    return { body, headers };
  };
  
  const result = await fetchResult();
  callback(result);

  return result;
}

// export async function postDataTo(teacher, callback) {
//     const fetchResult = async () => {
      
//         const response = await fetch("/api/v1/teachers/create", {
//         method: "POST",
//         headers: { "Content-Type": "application/json" },
//         body: JSON.stringify(
        
//     teacher
       
//       ),
//       });

//       const body = await response.json();
//       const headers = response.headers;
//       const status = response.status;
//       return { body, headers, status };
//     };
  
//     const result = await fetchResult();
//     callback(result);
//     console.log(result);
//     return result;
//   }

export async function postDataTo(teacher) {
  // console.log(teacher);
  const response = await fetch("/api/v1/teachers/create", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(teacher),
  });

  const body = await response.json();
  const headers = response.headers;
  const status = response.status;
  console.log(response);
  // return { response };
  return { body, headers, status };
}

export async function putDataTo(teacher) {
  // console.log(teacher);
  const response = await fetch("/api/v1/teachers/update?tid=" + teacher.id, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(teacher),
  });

  const result = await response.json();
  const headers = response.headers;
  const status = response.status;
 console.log(result);
  return { result };
  // return { body, headers, status };
}

export async function switchActive(id) {
  console.log(id);
  const response = await fetch(`/api/v1/teachers/active?tid=${id}&active=false` ,{
    method: "PATCH",
    headers: { "Content-Type": "application/json" },
    // body: JSON.stringify(teacher),
  });

  const body = await response.json();
  const headers = response.headers;
  const status = response.status;
  // console.log(response);
  // return { response };
  return { body, headers, status };
}
   
//    function draw(obj){
//     for (const prop in obj) {
//         console.log(`${prop}: ${obj[prop]}`);
//       }
//     }
 
//     draw(teacher.body);
//   draw(teacher.contacts);
//   draw(teacher.subjects);
//   draw(teacher.shift);

// fetch("/api/v1/teachers/create", {
//   method: "POST",
//   headers: {
//     "Content-Type": "application/json",
//   },
//   body: JSON.stringify({
//     id: 0,
//     fName: fName,
//     lName: lName,
//     active: true,
//     workHoursPerWeek: workHours,

//     contacts: teacherContacts,

//     teacherShiftDto: teacherShiftDto,
//     subjectsList: chosenSubjects,
//   }),
// }).then(applyResult);
