import {
  Button,
  Container,
  FormControl,
  Grid,
  InputLabel,
  MenuItem,
  OutlinedInput,
  Select,
  Stack,
  TextField,
} from "@mui/material";
import { useState } from "react";
import { Link } from "react-router-dom";

export function CreateModule() {
  const [number, setNumber] = useState("");
  const [name, setName] = useState("");

  const [isNameEmpty, setIsNameEmpty] = useState(false);
  const [isNameValid, setIsNameValid] = useState(true);

  const [isModuleCodeEmpty, setIsModuleCodeEmpty] = useState(false);
  const [isModuleCodeValid, setIsModuleCodeValid] = useState(true);

  const [successfulPost, setSuccessfulPost] = useState(true);

  const clear = () => {
    setNumber("");
    setName("");
  };

  const badSymbols = "!@#$%^&*_+={}<>|~`\\\"\'";

  const setAndCheckName = ((name) => {
    setName(name);
    if (name.length === 0) {
      setIsNameEmpty(true);
    }
    else {
      setIsNameEmpty(false);
    }
    const isValid = name.split('').some(char => badSymbols.includes(char));
    if (isValid) {
      setIsNameValid(false);
    }
    else {
      setIsNameValid(true);
    }
  })

  const setAndCheckModuleCode = ((moduleCode) => {
    setNumber(moduleCode);
    if (moduleCode.length === 0) {
      setIsModuleCodeEmpty(true);
    }
    else {
      setIsModuleCodeEmpty(false);
    }
    const containsBadSymbols = moduleCode.split('').some(char => badSymbols.includes(char));
    if (containsBadSymbols) {
      setIsModuleCodeValid(false);
    }
    else {
      setIsModuleCodeValid(true);
    }
  })

  const validation = () => {
    if (!isNameEmpty && isNameValid && !isModuleCodeEmpty && isModuleCodeValid) {
      createModule();
    }
  };

  const createModule = () => {
    fetch("/api/v1/modules", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        number,
        name,
      }),
    }).then(clear);
  };

  return (
    <Container>
      <h3>Pridėti naują modulį</h3>
      <form>
        <Grid container rowSpacing={2}>

        <Grid item lg={10}>
            <TextField
              fullWidth
              required
              error={!isModuleCodeValid || isModuleCodeEmpty}
              helperText={
                !isModuleCodeValid ? "Modulio kodas turi neleidžiamų simbolių." : 
                isModuleCodeEmpty ? "Modulio kodas negali būti tuščias" : null
              }
              variant="outlined"
              label="Modulio kodas"
              id="number"
              value={number}
              onChange={(e) => setAndCheckModuleCode(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item lg={10}>
            <TextField
              fullWidth
              required
              error={!isNameValid || isNameEmpty}
              helperText={
                !isNameValid ? "Modulio pavadinimas turi neleidžiamų simbolių." : 
                isNameEmpty ? "Modulio pavadinimas negali būti tuščias" : null
              }
              variant="outlined"
              label="Modulio pavadinimas"
              id="name"
              value={name}
              onChange={(e) => setAndCheckName(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item lg={10}>
            <Stack direction="row" spacing={2}>
              <Button variant="contained" onClick={validation}>
                Išsaugoti
              </Button>

              <Link to="/modules">
                <Button variant="contained">Grįžti</Button>
              </Link>
            </Stack>
          </Grid>
          <Grid item lg={10}>
          {/* {isPostUsed ? (
            successfulPost ? (
                <Alert severity="success"> Modulis sėkmingai pakeistas.</Alert>
                ) : 
                (
                <Grid>
                    <Alert severity="warning">Nepavyko pakeisti modulio.</Alert>
                    {Object.keys(shiftCreateMessageError).map(key => (
                    <Alert key={key} severity="warning"> {shiftCreateMessageError[key]} </Alert>
                    ))}
                </Grid>
                )
            ) : 
            (
            <div></div>
            )} */}
          </Grid>
        </Grid>
      </form>
    </Container>
  );
}