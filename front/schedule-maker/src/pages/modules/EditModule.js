import { Alert, Button, Grid, TextField } from "@mui/material";
import { Container, Stack } from "@mui/system";
import { useState, useEffect } from "react";
import { Link, useHref, useParams } from "react-router-dom";

export function EditModule() {
  const [module, setModule] = useState({});
  const [number, setNumber] = useState("");
  const [name, setName] = useState("");
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const listUrl = useHref("/modules");

  const [isNameEmpty, setIsNameEmpty] = useState(false);
  const [isNameValid, setIsNameValid] = useState(true);

  const [isModuleCodeEmpty, setIsModuleCodeEmpty] = useState(false);
  const [isModuleCodeValid, setIsModuleCodeValid] = useState(true);

  const [successfulPost, setSuccessfulPost] = useState();
  const [isPostUsed, setIsPostUsed] = useState(false);
  const [moduleCreateMessageError, setModuleCreateMessageError] = useState([]);

  
  const params = useParams({
    number: "",
    name: "",
  });

  const invalidSymbols = "!@#$%^&*_+={}<>|~`\\'";

  useEffect(() => {
    fetch(`api/v1/modules/${params.id}`)
      .then((response) => response.json())
      .then((data) => {
        setModule(data);
        setNumber(data.number);
        setName(data.name);
      });
  }, []);

  const deleteModule = (id) => {
    fetch("/api/v1/modules/" + id, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
    }).then(() => (window.location = listUrl));
  };

  const handleEditModule = () => {
    setError("");
    setSuccess("");
    if (!number) {
      setError("Prašome užpildyti modulio kodą.");
    } else if (number.split("").some((char) => invalidSymbols.includes(char))) {
      setError("Modulio numeris turi neleidžiamų simbolių.");
    } else if (!name) {
      setError("Prašome užpildyti modulio pavadinimą.");
    } else if (name.split("").some((char) => invalidSymbols.includes(char))) {
      setError("Modulio pavadinimas turi neleidžiamų simbolių.");
    } else {
      fetch(`/api/v1/modules/${params.id}`, {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          number,
          name,
        }),
      }).then(applyResult);
    }
  };

  const applyResult = (result) => {
    if (result.ok) {
      setSuccess("Sėkmingai atnaujinote!");
    } else {
      setError("Atnaujinti nepavyko!");
    }
  };

    const handleAfterPost = ((data) => {
      if ((Object.keys(data).length) === 0) {
          setSuccessfulPost(true);
      }
      else {
          setSuccessfulPost(false);
          setModuleCreateMessageError(data);
      }
      setIsPostUsed(true);
  })

  return (
    <Container>
      <h1>Redagavimas</h1>
      <h3>{module.name}</h3>
      <form>
        <Grid container rowSpacing={2}>
          <Grid item sm={10}>
            <TextField
              fullWidth
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

          <Grid item sm={10}>
            <TextField
              fullWidth
              error={!isNameValid || isNameEmpty}
              helperText={
                !isNameValid ? "Modulio pavadinimas turi neleidžiamų simbolių." : 
                isNameEmpty ? "Modulio pavadinimas negali būti tuščias" : null
              }
              multiline
              variant="outlined"
              label="Modulio pavadinimas"
              id="name"
              value={name}
              onChange={(e) => setAndCheckName(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item sm={10}>
            {error && <Alert severity="warning">{error}</Alert>}
            {success && <Alert severity="success">{success}</Alert>}
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
          <Grid item lg={10}>
                {isPostUsed ? (
                    successfulPost ? (
                        <Alert severity="success"> Modulis sėkmingai pakeistas.</Alert>
                        ) : 
                        (
                        <Grid>
                            <Alert severity="warning">Nepavyko pakeisti moduio.</Alert>
                            {Object.keys(moduleCreateMessageError).map(key => (
                            <Alert key={key} severity="warning"> {moduleCreateMessageError[key]} </Alert>
                            ))}
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
