import * as React from "react";
import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import {
  Button,
  Container,
  FormControl,
  Grid,
  Select,
  Stack,
  TextField,
  InputLabel,
  MenuItem,
  Alert,
} from "@mui/material";
import { SelectChangeEvent } from "@mui/material/Select";

export function UpdateClassroom() {
  const [classroom, setClassroom] = useState({});
  const [error, setError] = useState();
  const [success, setSuccess] = useState();
  const [active, setActive] = useState("");
  const [classroomName, setClassroomName] = useState("");
  const [description, setDescription] = useState("");
  const [building, setBuilding] = useState("AKADEMIJA");
  const invalidSymbols = "!@#$%^&*_+={}<>|~`\\\"'";

  const handleDescriptionChange = (event) => {
    setDescription(event.target.value);
  };

  const handleCNameeChange = (event) => {
    setClassroomName(event.target.value);
  };

  const handleChange = (event: SelectChangeEvent) => {
    setBuilding(event.target.value);
  };

  const params = useParams({
    classroomName: "",
    building: "",
    description: "",
    active: classroom.active,
  });

  useEffect(() => {
    fetch(`/api/v1/classrooms/classroom/${params.id}`)
      .then((response) => response.json())
      .then((data) => {
        setClassroom(data);
        setClassroomName(data.classroomName);
        setDescription(data.description);
        setBuilding(data.building);
      });
  }, []);

  const updateClassroom = () => {
    setError("");
    setSuccess("");
    if (!classroomName) {
      setError("Prašome užpildyti klasės pavadinimą.");
    } else if (
      classroomName.split("").some((char) => invalidSymbols.includes(char))
    ) {
      setError("Klasės pavadinimas turi neleidžiamų simbolių.");
    } else if (!description) {
      setError("Prašome užpildyti klasės aprašą.");
    } else if (
      description.split("").some((char) => invalidSymbols.includes(char))
    ) {
      setError("Klasės aprašas turi neleidžiamų simbolių.");
    } else if (!building) {
      setError("Prašome pasirinkti pastatą.");
    } else {
      fetch(`/api/v1/classrooms/update-classroom/${params.id}`, {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          description,
          classroomName,
          building,
        }),
      }).then((result) => {
        if (!result.ok) {
          setError("Redaguoti nepavyko!");
        } else {
          setSuccess("Sėkmingai atnaujinote!");
        }
      });
    }
  };

  const disableClassroom = () => {
    fetch(`/api/v1/classrooms/disable-classroom/${params.id}`, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
      },
    }).then(() => navigate(-1));
  };

  const enableClassroom = () => {
    fetch(`/api/v1/classrooms/enable-classroom/${params.id}`, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
      },
    }).then(() => navigate("/rooms"));
  };

  const updateProperty = (property, event) => {
    setClassroom({
      ...classroom,
      [property]: event.target.value,
    });
  };

  return (
    <div>
      <Container>
          <h1 className="edit-header">Redagavimas</h1>
          <h3>{classroom.classroomName}</h3>
          <span id="modified-date">
            Paskutinį kartą redaguota: {classroom.modifiedDate}
          </span>
        <form>
          <Grid container rowSpacing={3}>
            <Grid item sm={10}>
              <FormControl fullWidth>
                <InputLabel id="building-label">Pastatas</InputLabel>
                <Select
                  labelId="building-label"
                  id="building"
                  label="Pastatas"
                  value={building}
                  onChange={handleChange}
                >
                  <MenuItem value="AKADEMIJA">AKADEMIJA</MenuItem>
                  <MenuItem value="TECHIN">TECHIN</MenuItem>
                </Select>
              </FormControl>
            </Grid>
            <Grid item sm={10}>
              <TextField
                fullWidth
                variant="outlined"
                id="classroomName"
                label="Klasės pavadinimas"
                value={classroomName}
                onChange={(e) => setClassroomName(e.target.value)}
              ></TextField>
            </Grid>
            <Grid item sm={10}>
              <TextField
                fullWidth
                multiline
                variant="outlined"
                label="Klasės aprašas"
                id="description"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
              ></TextField>
            </Grid>
            <Grid item sm={10}>
              {" "}
              <legend>{params.classroomName}</legend>
              {error && <Alert severity="warning">{error}</Alert>}
              {success && <Alert severity="success">{success}</Alert>}
            </Grid>
              <Stack direction="row" spacing={2}>
                <Button variant="contained" onClick={updateClassroom}>
                  Išsaugoti
                </Button>
                {!classroom.active && (
                  <Button
                    variant="contained"
                    data-value="true"
                    value={params.id}
                    onClick={enableClassroom}
                  >
                    Aktyvuoti
                  </Button>
                )}
                {classroom.active && (
                  <Link to="/rooms">
                    <Button
                      variant="contained"
                      data-value="true"
                      value={params.id}
                      onClick={disableClassroom}
                    >
                      Ištrinti
                    </Button>
                  </Link>
                )}
                <Link to="/rooms">
                  <Button variant="contained">Grįžti</Button>
                </Link>
              </Stack>
            </Grid>
        </form>
      </Container>
    </div>
  );
}
