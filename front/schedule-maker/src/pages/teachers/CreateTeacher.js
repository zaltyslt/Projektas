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

  export function CreateTeacher() {
    const [fname, setFName] = useState("");
    const [lname, setLName] = useState("");
    const [nickName, setNickName] = useState("");
    

  }

  const applyResult = (result) => {
    if (result.ok) {
      setSuccess("Sėkmingai pridėta!");
      clear();
    } else {
      setError("Nepavyko sukurti!");
    }
  };

  const createTeacher = () => {
    fetch("/api/v1/teachers", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        fName,
        lname,
        nickName
      }),
    }).then(applyResult);
  };

  return (
    <Container>
      <h3 className="create-header">Pridėti naują dėstytoją</h3>
      <form>
        
        <Grid container rowSpacing={2}>
          <Grid item sm={10}>
            <TextField
              fullWidth
              required
              variant="outlined"
              label="Dėstytojo vardas"
              id="fName"
              value={fName}
              onChange={(e) => setFName(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item sm={10}>
            <TextField
              fullWidth
              required
              variant="outlined"
              label="Modulio pavadinimas"
              id="name"
              value={name}
              onChange={(e) => setName(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item sm={10}>
            {error && <Alert severity="warning">{error}</Alert>}
            {success && <Alert severity="success">{success}</Alert>}
            <Stack direction="row" spacing={2}>
              <Button variant="contained" onClick={validation}>
                Išsaugoti
              </Button>

              <Link to="/modules">
                <Button variant="contained">Grįžti</Button>
              </Link>
            </Stack>
          </Grid>
        </Grid>
      </form>
    </Container>
  );
}