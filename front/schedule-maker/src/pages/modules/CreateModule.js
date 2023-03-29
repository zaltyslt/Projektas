import {
  Alert,
  Button,
  Container,
  Grid,
  Stack,
  TextField,
} from "@mui/material";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import ".././pages.css";

export function CreateModule() {
  const [number, setNumber] = useState("");
  const [name, setName] = useState("");
  const [nameError, setNameError] = useState(false);
  const [numberError, setNumberError] = useState(false);
  const [nameNotValid, setNameNotValid] = useState(false);
  const [numberNotValid, setNumberNotValid] = useState(false);
  const [error, setError] = useState("");
  const [createMessage, setCreateMessage] = useState("");
  const clear = () => {
    setNumber("");
    setName("");
  };

  const validation = () => {
    setCreateMessage("");
    const badSymbols = "!@#$%^&*_+={}<>|~`\\'";
    let notValidName = name.split("").some((char) => badSymbols.includes(char));
    let notValidNumber = number
      .split("")
      .some((char) => badSymbols.includes(char));
    if (number === "" && name === "") {
      setNameError(true);
      setNumberError(true);
    } else if (name === "") {
      setNameError(true);
    } else if (notValidName) {
      setNameError(false);
      setNameNotValid(true);
    } else if (number === "") {
      setNumberError(true);
    } else if (notValidNumber) {
      setNumberError(false);
      setNumberNotValid(true);
    } else {
      createModule();
    }
  };

  const createModule = async () => {
    await fetch("api/v1/modules/create", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        number,
        name,
      }),
    }).then((response) => {
      let success = response.ok;
      response.json().then((response) => {
        if (!success) {
          setCreateMessage("");
          setError(response.message);
        } else {
          setCreateMessage("Modulis sėkmingai sukurtas. ");
          setError("");
          clear();
        }
      });
    });
  };

  return (
    <Container>
      <h3 className="create-header">Pridėti naują modulį</h3>
      <form>
        <Grid container rowSpacing={2}>
          <Grid item sm={8}>
            <TextField
              fullWidth
              required
              variant="outlined"
              error={numberError || numberNotValid}
              helperText={
                numberError
                  ? "Modulio kodas yra privalomas"
                  : nameNotValid
                    ? "Modulio kodas turi negalimų simbolių. "
                    : ""
              }
              label="Modulio kodas"
              id="number"
              value={number}
              onChange={(e) => setNumber(e.target.value)}
            ></TextField>
          </Grid>
          <Grid item sm={8}>
            <TextField
              fullWidth
              required
              variant="outlined"
              error={nameError || nameNotValid}
              helperText={
                nameError
                  ? "Modulio pavadinimas yra privalomas"
                  : nameNotValid
                    ? "Laukas turi negalimų simbolių. "
                    : ""
              }
              label="Modulio pavadinimas"
              id="name"
              value={name}
              onChange={(e) => setName(e.target.value)}
            ></TextField>
          </Grid>
          <Grid item sm={8} marginTop={2}>
            <Stack direction="row" spacing={2}>
              <Button
                id="save-button-create-module"
                variant="contained"
                onClick={validation}
              >
                Išsaugoti
              </Button>
              <Link to="/modules">
                <Button id="back-button-create-module" variant="contained">
                  Grįžti
                </Button>
              </Link>
            </Stack>
          </Grid>
          <Grid item sm={8}>
            {error && <Alert severity="warning">{error}</Alert>}
            {createMessage && <Alert severity="success">{createMessage}</Alert>}
          </Grid>
        </Grid>
      </form>
    </Container>
  );
}
