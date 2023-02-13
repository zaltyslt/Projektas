import React from "react";
import { useState, useEffect } from "react";
import { MDBBtn, MDBContainer } from "mdb-react-ui-kit";
import team1 from "./images/team1.jpg";

export function App2() {
  const [serverResponse, setServerResponse] = useState([]);
  const [showText, setShowText] = useState(false);
  const [smt, setSmt] = useState(  JSON.parse(window.localStorage.getItem("active")) || 0 );

  useEffect(() => {
    setSmt(JSON.parse(window.localStorage.getItem("active")));
  }, []);

  useEffect(() => {
    window.localStorage.setItem("active", smt);
  }, [smt]);

  useEffect(() => {
    if (!showText) {
      setShowText(true);
      setTimeout(() => {
        if (serverResponse.answer) {
          console.log(serverResponse);
          setShowText(true);
          // window.alert(serverResponse.answer);
        } else {
          console.log(serverResponse);
          setShowText(false);
          // window.alert('No connection !!!');
        }
      }, 1000);
    } else {
      setShowText(false);
      setSmt(0);
    }
  }, [serverResponse]);

  const fetchServerOnline = async () => {
    fetch("/hello")
      .then((response) => response.json())
      .then((responseJson) => setServerResponse(responseJson));
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
            onClick={fetchServerOnline}
          >
            Check server connection <i className="fas fa-download ms-1"></i>
          </button>

          <div id="serverResponse">{showText && serverResponse.answer}</div>
          <div>
            <p>Count = {smt} </p>
            <button
              type="button"
              className="btn btn-primary btn-rounded"
              onClick={() => setSmt(smt + 1)}
            >
              Check server connection <i className="fas fa-download ms-1"></i>
            </button>
          </div>
        </div>
      </div>
    </MDBContainer>
  );
}