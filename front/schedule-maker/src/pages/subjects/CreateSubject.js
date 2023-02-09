import {
  MDBBtn,
  MDBCol,
  MDBContainer,
  MDBInput,
  MDBRow,
  MDBTextArea,
} from "mdb-react-ui-kit";
import { useState } from "react";

export function CreateSubject() {
  const [name, setName] = useState("");
  const [module, setModule] = useState({});
  const [description, setDescription] = useState("");

  const clear = () => {
    setName("");
    setDescription("");
  };

  const createSubject = () => {
    fetch("/api/v1/subjects", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        name,
        description,
      }),
    }).then(clear);
  };

  return (
    <div>
      <MDBContainer>
        <h3>Pridėti naują dalyką</h3>
        <form>
          <MDBInput
            wrapperClass="mb-4"
            id="name"
            value={name}
            label="Dalyko pavadinimas"
            onChange={(e) => setName(e.target.value)}
          />

          <MDBTextArea
            wrapperClass="mb-4"
            id="description"
            rows={4}
            value={description}
            label="Aprašas"
            onChange={(e) => setDescription(e.target.value)}
          />

          <MDBBtn type="submit" onClick={createSubject}>
            Išsaugoti
          </MDBBtn>
        </form>
      </MDBContainer>
    </div>
  );
}
