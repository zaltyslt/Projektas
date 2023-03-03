import {
  Alert,
  Button,
  Container,
  Grid,
  Stack,
  TextField,
} from "@mui/material";
import { useState } from "react";
import { Link } from "react-router-dom";
import ".././pages.css"

export function CreateModule() {
  const [number, setNumber] = useState("");
  const [name, setName] = useState("");

  const [isValidNumber, setIsValidNumber] = useState(true);
  const [isNumberEmpty, setIsNumberEmpty] = useState(false);
  const [isNumberTooLong, setIsNumberTooLong] = useState(false);

  const [isValidName, setIsValidName] = useState(true);
  const [isNameEmpty, setIsNameEmpty] = useState(false);
  const [isNameTooLong, setIsNameTooLong] = useState(false);

  const [successfulPost, setSuccessfulPost] = useState();
  const [isPostUsed, setIsPostUsed] = useState(false);
  const [moduleErrors, setModuleErrors] = useState();


  const validation = () => {
    if (isValidNumber && isValidName && !isNumberEmpty && !isNameEmpty && !isNumberTooLong && !isNameTooLong) {
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
    })
    .then(response => response.json())
    .then(applyResult);
  };

  const applyResult = (data) => {
    if (data.valid) {
      setSuccessfulPost(true);
      setName("");
      setNumber("");
    }
    else {
        setModuleErrors(data)
        setSuccessfulPost(false);
    }
    setIsPostUsed(true);
  };

  const badSymbols = "!@#$%^&*_+={}<>|~`\\'";
  const moduleNumberLength = 200;
  const moduleNameLength = 200;


  const setNumberOnChange = (number) => {
    setNumber(number);
    (number.length === 0) ? setIsNumberEmpty(true) : setIsNumberEmpty(false);
   
    const isValid = number.split('').some(char => badSymbols.includes(char));
    (isValid) ? setIsValidNumber(false) : setIsValidNumber(true);
 
    (number.length > moduleNumberLength) ? setIsNumberTooLong(true) : setIsNumberTooLong(false);
  }

  const setNameOnChange = (name) => {
    setName(name);
    (name.length === 0) ? setIsNameEmpty(true) : setIsNameEmpty(false);
   
    const isValid = name.split('').some(char => badSymbols.includes(char));
    (isValid) ? setIsValidName(false) : setIsValidName(true);
  
    (name.length > moduleNameLength) ? setIsNameTooLong(true) : setIsNameTooLong(false);
  }

  return (
    <Container>
      <h3 className="create-header">Pridėti naują modulį</h3>
      <form>
        
        <Grid container rowSpacing={2}>
          <Grid item sm={10}>
            <TextField
              fullWidth
              required
              variant="outlined"
              error={!isValidNumber || isNumberEmpty || isNumberTooLong}
              helperText={
                !isValidNumber ? "Modulio kodas turi neleidžiamų simbolių." : 
                isNumberEmpty ? "Modulio kodas negali būti tuščias" :
                isNumberTooLong ? `Modulio kodas negali būti ilgesnis nei ${moduleNumberLength} simbolių` 
                : null
              }
              label="Modulio kodas"
              id="number"
              value={number}
              onChange={(e) => setNumberOnChange(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item sm={10}>
            <TextField
              fullWidth
              required
              variant="outlined"
              error={!isValidName || isNameEmpty || isNameTooLong}
              helperText={
                !isValidName ? "Modulio pavadinimas turi neleidžiamų simbolių." : 
                isNameEmpty ? "Modulio pavadinimas negali būti tuščias" :
                isNameTooLong ? `Modulio pavadinimas negali būti ilgesnis nei ${moduleNameLength} simbolių` 
                : null
              }
              label="Modulio pavadinimas"
              id="name"
              value={name}
              onChange={(e) => setNameOnChange(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item sm={10}>
            <Stack direction="row" spacing={2}>
              <Button variant="contained" onClick={validation}>
                Išsaugoti
              </Button>

              <Link to="/modules">
                <Button variant="contained">Grįžti</Button>
              </Link>
            </Stack>
          </Grid>

          <Grid item sm={10}>
                {isPostUsed ? (
                    successfulPost ? (
                        <Alert severity="success"> Modulis sėkmingai pridėtas.</Alert>
                        ) : 
                        (
                        <Grid>
                            <Alert severity="warning">Nepavyko pridėti modulio.</Alert>
                            {
                                (moduleErrors.passedValidation ?
                                    (moduleErrors.databaseErrors).map((databaseError, index) => (
                                        <Alert key={index} severity="warning">
                                        {databaseError}
                                        </Alert>
                                    )) 
                                    :
                                    Object.keys(moduleErrors.validationErrors).map(key => (
                                    <Alert key={key} severity="warning"> {moduleErrors.validationErrors[key]} {key} laukelyje.
                                    </Alert>
                                    ))
                                )
                            }
                        </Grid>
                        )
                    ) : 
                    (
                    <div></div>
                    )}
            </Grid>
        </Grid>
      </form>
    </Container>
  );
}
