import {
  Alert,
  Button,
  FormControl,
  Grid,
  InputLabel,
  MenuItem,
  OutlinedInput,
  Select,
  TextField,
} from "@mui/material";
import { Container, Stack } from "@mui/system";
import { useState, useEffect } from "react";
import { Link, useHref, useParams } from "react-router-dom";

export function EditSubject() {
  const [subject, setSubject] = useState({
    name: "",
    description: "",
    module: {},
    classRooms: [],
  });
  const params = useParams();
  const [modules, setModules] = useState([]);
  const [module, setModule] = useState("");
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [classRooms, setClassRooms] = useState([]);
  const [rooms, setRooms] = useState([]);
  const [nameError, setNameError] = useState(false);
  const [descriptionError, setDescriptionError] = useState(false);
  const [moduleError, setModuleError] = useState(false);
  const [classRoomError, setClassRoomError] = useState(false);
  const [nameNotValid, setNameNotValid] = useState(false);
  const [descriptionNotValid, setDescriptionNotValid] = useState(false);
  const [createMessage, setCreateMessage] = useState("");
  const [error, setError] = useState("");
  const listUrl = useHref("/subjects");

  useEffect(() => {
    fetch("api/v1/subjects/" + params.id)
      .then((response) => response.json())
      .then((data) => {
        setSubject(data);
        setDescription(data.description);
        setName(data.name);
      });
  }, []);

  useEffect(() => {
    fetch("api/v1/modules")
      .then((response) => response.json())
      .then(setModules);
  }, []);

  useEffect(() => {
    fetch("api/v1/classrooms/active")
      .then((response) => response.json())
      .then(setRooms);
  }, []);

  const handleRoomInput = (event) => {
    const {
      target: { value },
    } = event;
    setClassRooms(typeof value === "string" ? value.split(",") : value);
  };

  const deleteSubject = (id) => {
    fetch("api/v1/subjects/delete/" + id, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
      },
    }).then(() => (window.location = listUrl));
  };

  const validation = () => {
    setCreateMessage("");
    const badSymbols = "!@#$%^&*_+={}<>|~`\\'";
    let notValidName = name.split("").some((char) => badSymbols.includes(char));
    let notValiDescription = description
      .split("")
      .some((char) => badSymbols.includes(char));

    if (
      name === "" &&
      description === "" &&
      module === "" &&
      classRooms.length === 0
    ) {
      setNameError(true);
      setDescriptionError(true);
      setModuleError(true);
      setClassRoomError(true);
    } else if (name === "") {
      setNameError(true);
    } else if (notValidName) {
      setNameError(false);
      setNameNotValid(true);
    } else if (description === "") {
      setNameNotValid(false);
      setDescriptionError(true);
    } else if (notValiDescription) {
      setDescriptionError(false);
      setDescriptionNotValid(true);
    } else if (module === "") {
      setDescriptionNotValid(false);
      setNameNotValid(false);
      setModuleError(true);
    } else if (classRooms.length === 0) {
      setDescriptionNotValid(false);
      setNameNotValid(false);
      setClassRoomError(true);
    } else {
      editSubject();
    }
  };

  const editSubject = () => {
    fetch(`api/v1/subjects/${params.id}`, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        name,
        description,
        module,
        classRooms,
      }),
    }).then((response) => {
      // let statusCode = response.status;
      let success = response.ok;

      response.json().then((response) => {
        if (!success) {
          setCreateMessage("");
          setError(response.message);
        } else {
          setModuleError(false);
          setClassRoomError(false);
          setCreateMessage("Sėkmingai atnaujinta. ");
          setError("");
        }
      });
    });
  };

  return (
    <Container>
      <h1 className="edit-header">Redagavimas</h1>
      <h3>{subject.name}</h3>
      <span id="modified-date">
        Paskutinį kartą redaguota: {subject.modifiedDate}
      </span>
        <Grid container rowSpacing={2}>
          <Grid item sm={8}>
            <TextField
              fullWidth
              required
              variant="outlined"
              label="Dalyko pavadinimas"
              error={nameError || nameNotValid}
              helperText={
                nameError
                  ? "Dalyko pavadinimas yra privalomas"
                  : nameNotValid
                  ? "Laukas turi negalimų simbolių. "
                  : ""
              }
              id="name"
              value={name}
              onChange={(e) => setName(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item sm={8}>
            <TextField
              fullWidth
              multiline
              required
              variant="outlined"
              error={descriptionError || descriptionNotValid}
              helperText={
                descriptionError
                  ? "Dalyko aprašas yra privalomas. "
                  : descriptionNotValid
                  ? "Laukas turi negalimų simbolių. "
                  : ""
              }
              label="Dalyko aprašas"
              id="description"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item sm={8}>
            <FormControl fullWidth required error={moduleError}>
              <InputLabel id="module-label">
                {moduleError
                  ? "Privaloma pasirinkti modulį. "
                  : "Modulio pavadinimas"}
              </InputLabel>
              <Select
                label="Modulio pavadinimas"
                labelId="module-label"
                id="module"
                value={module}
                onChange={(e) => setModule(e.target.value)}
              >
                {modules.map((module) => (
                  <MenuItem key={module.id} value={module}>
                    {module.name}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          </Grid>

          <Grid item sm={8}>
            <FormControl fullWidth required error={classRoomError}>
              <InputLabel id="room-label">
                {classRoomError
                  ? "Privaloma pasirinkti nors vieną klasę. "
                  : "Klasės"}
              </InputLabel>
              <Select
                multiple
                labelId="room-label"
                id="room"
                value={classRooms}
                onChange={handleRoomInput}
                input={<OutlinedInput label="Klasės" />}
              >
                {rooms.map((room) => (
                  <MenuItem key={room.id} value={room}>
                    {room.classroomName}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          </Grid>

          <Grid item sm={8}>
            {error && <Alert severity="warning">{error}</Alert>}
            {createMessage && <Alert severity="success">{createMessage}</Alert>}
          </Grid>

          <Grid item sm={8}>
            <Stack direction="row" spacing={2}>
              <Button variant="contained" onClick={validation}>
                Išsaugoti
              </Button>

              <Button
                variant="contained"
                onClick={() => deleteSubject(subject.id)}
              >
                Ištrinti
              </Button>

              <Link to="/subjects">
                <Button variant="contained">Grįžti</Button>
              </Link>
            </Stack>
          </Grid>
        </Grid>
    </Container>
  );
}
