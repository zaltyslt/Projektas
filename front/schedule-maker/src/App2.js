import React from "react";
import { useState, useEffect } from "react";
import { MDBBtn, MDBContainer } from "mdb-react-ui-kit";
import team1 from "./images/team1.jpg";

export function App2() {
  const [serverResponse, setServerResponse] = useState([]);

  const fetchServerOnline = () => {
    fetch("/hello")
      .then((response) => response.json())
      .then((response) => setServerResponse(response));
     
      if (serverResponse) {
        window.alert(serverResponse.answer);
    } else {
      window.alert('No connection !!!');
    }
    
  };
  
  return (
    <MDBContainer fluid>
      <div
        className="d-flex justify-content-center align-items-center"
        style={{ height: "100vh" }}
      >
        <div className="text-center">
          <img
            className="mb-4"
            src={team1}
            style={{ width: 250, height: 100 }}
          />
          <h5 className="mb-3">Everything starts from a small step</h5>

          <button
            type="button"
            className="btn btn-primary btn-rounded"
            onClick={() => fetchServerOnline()}
          >
            Check server connection <i className="fas fa-download ms-1"></i>
          </button>
          {/* <MDBBtn 
            tag='a'href='https://mdbootstrap.com/docs/standard/getting-started/'             target='_blank'
            role='button'
          >
            Check server connection
          </MDBBtn> */}
        </div>
      </div>
    </MDBContainer>
  );
}
