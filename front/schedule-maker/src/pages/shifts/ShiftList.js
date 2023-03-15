import {
  Button,
  Checkbox,
  FormControlLabel,
  FormGroup,
  Grid,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableFooter,
  TableHead,
  TablePagination,
  TableRow,
  TextField,
} from "@mui/material";
import { Container } from "@mui/system";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import Stack from "@mui/material/Stack";
import ".././pages.css"

export function ShiftList() {
  const [activeShifts, setActiveShifts] = useState([]);
  const [inactiveShifts, setInactiveShifts] = useState([]);

  const [currentActiveShifts, setCurrentActiveShifts] = useState([]);

  const [rowsPerPageActive, setRowsPerPageActive] = useState(10);
  const [currentPageActive, setCurrentPageActive] = useState(0);

  const [rowsPerPageInactive, setRowsPerPageInactive] = useState(10);
  const [currentPageInactive, setCurrentPageInactive] = useState(0);

  const [isChecked, setIsChecked] = useState(false);

  const emptyRowsActive =
    currentPageActive > 0
      ? Math.max(
          0,
          (1 + currentPageActive) * rowsPerPageActive -
            currentActiveShifts.length
        )
      : 0;
  const emptyRowsInactive =
    currentPageInactive > 0
      ? Math.max(
          0,
          (1 + currentPageInactive) * rowsPerPageInactive -
            currentActiveShifts.length
        )
      : 0;

  const handleChangeRowsPerPageActive = (event) => {
    setRowsPerPageActive(parseInt(event.target.value, 10));
    setCurrentPageActive(0);
  };

  const handleChangeRowsPerPageInactive = (event) => {
    setRowsPerPageInactive(parseInt(event.target.value, 10));
    setCurrentPageInactive(0);
  };

  const handleChangePageActive = (event, newPage) => {
    setCurrentPageActive(newPage);
  };

  const handleChangePageInactive = (event, newPage) => {
    setCurrentPageInactive(newPage);
  };

  useEffect(() => {
    getActiveShifts();
    getInactiveShifts();
  }, []);

  const getActiveShifts = () => {
    fetch("api/v1/shift/get-active")
      .then((response) => response.json())
      .then((data) => {
        if (Array.isArray(data)) {
          setActiveShifts(data);
        } else {
          setActiveShifts([data]);
        }
      });
  };

  const getInactiveShifts = () => {
    fetch("api/v1/shift/get-inactive")
      .then((response) => response.json())
      .then((data) => {
        if (Array.isArray(data)) {
          setInactiveShifts(data);
        } else {
          setInactiveShifts([data]);
        }
      });
  };

  const activateShift = async (shiftID) => {
    await fetch(
      `api/v1/shift/activate-shift/${shiftID}`,
      {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
    getActiveShifts();
    getInactiveShifts();
  };

  useEffect(() => {
    if (activeShifts) {
      setCurrentActiveShifts(activeShifts);
    }
  }, [activeShifts]);

  const filterActiveShifts = (filterString) => {
    if (filterString.length === 0) {
      setCurrentActiveShifts(activeShifts);
    }
    if (filterString.length !== 0) {
      var shiftsTemp = activeShifts.filter((shift) =>
        shift.name.toLowerCase().includes(filterString.toLowerCase())
      );
      setCurrentActiveShifts(shiftsTemp);
    }
    setCurrentPageActive(0);
  };

  return (
    <div>
      <Container maxWidth="lg">
        <Grid container rowSpacing={3}>
          <Grid item sm={10}>
            <h3>Pamainų sąrašas</h3>
          </Grid>
          <Grid item sm={2}>
            <Stack direction="row" justifyContent="flex-end">
              <Link to="/add-shift">
                <Button variant="contained">Pridėti naują</Button>
              </Link>
            </Stack>
          </Grid>

          <Grid item sm={12}>
            <TextField
              fullWidth
              variant="outlined"
              name="search-form"
              label="Paieška"
              id="search-form"
              onChange={(e) => filterActiveShifts(e.target.value)}
            ></TextField>
          </Grid>
        </Grid>

        <TableContainer component={Paper}>
          <Table aria-label="custom pagination table">
            <TableHead>
              <TableRow>
                <TableCell style={{ width: "500px" }} >Pamainos pavadinimas</TableCell>
                <TableCell>Pamainos laikas</TableCell>
              </TableRow>
            </TableHead>

            <TableBody>
              {(rowsPerPageActive > 0
                ? currentActiveShifts.slice(
                    currentPageActive * rowsPerPageActive,
                    currentPageActive * rowsPerPageActive + rowsPerPageActive
                  )
                : currentActiveShifts
              ).map((shift) => (
                <TableRow key={shift.id}>
                  <TableCell component="th" scope="row">
                    <Link to={"/view-shift/" + shift.id}>{shift.name}</Link>
                  </TableCell>
                  <TableCell>{shift.shiftStartingTime + "-" + shift.shiftEndingTime}</TableCell>
                  <TableCell></TableCell>
                </TableRow>
              ))}

              {emptyRowsActive > 0 && (
                <TableRow style={{ height: 53 * emptyRowsActive }}>
                  <TableCell colSpan={6} />
                </TableRow>
              )}
            </TableBody>

            <TableFooter>
              <TableRow>
                <TablePagination
                  labelRowsPerPage="Rodyti po"
                  labelDisplayedRows={({ from, to, count }) =>
                    `${from}-${to} iš ${count}`
                  }
                  rowsPerPageOptions={[10, 20, { label: "Visi", value: -1 }]}
                  colSpan={3}
                  count={currentActiveShifts.length}
                  page={currentPageActive}
                  SelectProps={{
                    inputProps: {
                      "aria-label": "Rodyti po",
                    },
                    native: true,
                  }}
                  onPageChange={handleChangePageActive}
                  rowsPerPage={rowsPerPageActive}
                  onRowsPerPageChange={handleChangeRowsPerPageActive}
                ></TablePagination>
              </TableRow>
            </TableFooter>
          </Table>
        </TableContainer>

        <FormGroup>
          <FormControlLabel
            control={<Checkbox />}
            label="Ištrintos pamainos"
            onChange={(e) =>
              e.target.checked ? setIsChecked(true) : setIsChecked(false)
            }
          />
        </FormGroup>
        {isChecked && (
          <TableContainer component={Paper}>
            <Table aria-label="custom pagination table">
              <TableHead>
                <TableRow>
                  <TableCell style={{ width: "500px" }}>Pamainos pavadinimas</TableCell>
                  <TableCell style={{ width: "500px" }} >Pamainos laikas</TableCell>
                  <TableCell ></TableCell>
                </TableRow>
              </TableHead>

              <TableBody>
                {(rowsPerPageInactive > 0
                  ? inactiveShifts.slice(
                      currentPageInactive * rowsPerPageInactive,
                      currentPageInactive * rowsPerPageInactive +
                        rowsPerPageInactive
                    )
                  : inactiveShifts
                ).map((shift) => (
                  <TableRow key={shift.id}>
                    <TableCell component="th" scope="row">{shift.name}</TableCell>
                    <TableCell component="th" scope="row">{shift.shiftTime}</TableCell>
                    <TableCell align="center" className="activity">
                      <Button
                        variant="contained"
                        onClick={() => activateShift(shift.id)}
                      >
                        Atstatyti
                      </Button>
                    </TableCell>
                  </TableRow>
                ))}

                {emptyRowsInactive > 0 && (
                  <TableRow style={{ height: 53 * emptyRowsInactive }}>
                    <TableCell colSpan={6} />
                  </TableRow>
                )}
              </TableBody>
              <TableFooter>
                <TableRow>
                  <TablePagination
                    labelRowsPerPage="Rodyti po"
                    rowsPerPageOptions={[10, 20, { label: "Visi", value: -1 }]}
                    colSpan={3}
                    count={inactiveShifts.length}
                    page={currentPageInactive}
                    SelectProps={{
                      inputProps: {
                        "aria-label": "Rodyti po",
                      },
                      native: true,
                    }}
                    onPageChange={handleChangePageInactive}
                    rowsPerPage={rowsPerPageInactive}
                    onRowsPerPageChange={handleChangeRowsPerPageInactive}
                  ></TablePagination>
                </TableRow>
              </TableFooter>
            </Table>
          </TableContainer>
        )}
      </Container>
    </div>
  );
}
