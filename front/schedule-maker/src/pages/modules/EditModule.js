import { Alert, Button, Grid, TextField } from "@mui/material";
import { Container, Stack } from "@mui/system";
import { useState, useEffect } from "react";
import { Link, useHref, useParams } from "react-router-dom";

export function EditModule() {
  const [module, setModule] = useState({});
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


  const listUrl = useHref("/modules");

  const params = useParams({
    number: "",
    name: "",
  });

  const badSymbols = "!@#$%^&*_+={}<>|~`\\'";
  const moduleNumberLength = 200;
  const moduleNameLength = 200;

  useEffect(() => {
    fetch(`api/v1/modules/${params.id}`)
      .then((response) => response.json())
      .then((data) => {
        setModule(data);
        setNumber(data.number);
        setName(data.name);
      });
  }, []);

  const deleteModule = async (id) => {
    await fetch("/api/v1/modules/" + id, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
    }).then(() => (window.location = listUrl));
  };

  const handleEditModule = () => {
    if (isValidNumber && isValidName && !isNumberEmpty && !isNameEmpty && !isNumberTooLong && !isNameTooLong) {
      editModule();
    }
  }

  const editModule = async () => {
      await fetch(`/api/v1/modules/update/${params.id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          number,
          name,
        }),
      })
      .then(response => response.json())
      .then(result => {
        applyResult(result);
    });
  };

  useEffect(() => {
    (number.length === 0) ? setIsNumberEmpty(true) : setIsNumberEmpty(false);
   
    const isValid = number.split('').some(char => badSymbols.includes(char));
    (isValid) ? setIsValidNumber(false) : setIsValidNumber(true);
  
    (number.length > moduleNumberLength) ? setIsNumberTooLong(true) : setIsNumberTooLong(false);
  },[number])

  useEffect(() => {
    (name.length === 0) ? setIsNameEmpty(true) : setIsNameEmpty(false);
   
    const isValid = name.split('').some(char => badSymbols.includes(char));
    (isValid) ? setIsValidName(false) : setIsValidName(true);
  
    (name.length > moduleNameLength) ? setIsNameTooLong(true) : setIsNameTooLong(false);
  },[name])

  const applyResult = (data) => {
    if (data.valid) {
      setSuccessfulPost(true);
    }
    else {
        setModuleErrors(data)
        setSuccessfulPost(false);
    }
    setIsPostUsed(true);
  };

  return (
    <Container>
      <h1 className="edit-header">Redagavimas</h1>
      <h3>{module.name}</h3>
      <span id="modified-date">Paskutinį kartą redaguota: {module.modifiedDate}</span>
      <form>
        <Grid container rowSpacing={2}>
          <Grid item sm={10}>
            <TextField
              fullWidth
              variant="outlined"
              required
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
              onChange={(e) => setNumber(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item sm={10}>
            <TextField
              fullWidth
              multiline
              variant="outlined"
              required
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
              onChange={(e) => setName(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item sm={10}>
            <Stack direction="row" spacing={2}>
              <Button
                variant="contained"
                onClick={() => handleEditModule(module.id)}
              >
                Išsaugoti
              </Button>

              <Button
                variant="contained"
                onClick={() => deleteModule(module.id)}
              >
                Ištrinti
              </Button>

              <Link to="/modules">
                <Button variant="contained">Grįžti</Button>
              </Link>
            </Stack>
          </Grid>

          <Grid item sm={10}>
                {isPostUsed ? (
                    successfulPost ? (
                        <Alert severity="success"> Modulis sėkmingai pakeistas.</Alert>
                        ) : 
                        (
                        <Grid>
                            <Alert severity="warning">Nepavyko pakeisti modulio.</Alert>
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
