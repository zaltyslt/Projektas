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
import { Container, Stack } from "@mui/system";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

export function GroupList() {
  const [groups, setGroups] = useState([]);
  const [deletedGroups, setDeletedGroups] = useState([]);
  const [filteredGroups, setFilteredGroups] = useState([]);
  const [filteredDeletedGroups, setFilteredDeletedGroups] = useState([]);

  const [page, setPage] = useState(0);
  const [pageInDeleted, setPageInDeleted] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [rowsPerPageInDeleted, setRowsPerPageInDeleted] = useState(10);
  const [isChecked, setChecked] = useState(false);

  useEffect(() => {
    fetchGroups();
  }, []);

  useEffect(() => {
    fetchDeletedGroups();
  }, []);

  const fetchDeletedGroups = () => {
    fetch("api/v1/group/get-inactive")
      .then((response) => response.json())
      .then((data) => {
        setDeletedGroups(data);
        setFilteredDeletedGroups(data);
      });
  };

  const fetchGroups = () => {
    fetch("api/v1/group/get-active")
      .then((response) => response.json())
      .then((data) => {
        setGroups(data);
        setFilteredGroups(data);
      });
  };

  const handleRestore = (id) => {
    fetch("api/v1/group/activate-group/" + id, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then(fetchGroups)
      .then(fetchDeletedGroups);
  };

  const emptyRows =
    page > 0
      ? Math.max(0, (1 + page) * rowsPerPage - filteredGroups.length)
      : 0;

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const emptyRowsInDeleted =
    pageInDeleted > 0
      ? Math.max(
          0,
          (1 + pageInDeleted) * rowsPerPageInDeleted -
            filteredDeletedGroups.length
        )
      : 0;

  const handleChangePageInDeleted = (event, newPage) => {
    setPageInDeleted(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const handleChangeRowsPerPageInDeleted = (event) => {
    setRowsPerPageInDeleted(parseInt(event.target.value, 10));
    setPageInDeleted(0);
  };

  const handleSearch = (filterString) => {
    if (filterString.length === 0) {
      setFilteredGroups(groups);
      setFilteredDeletedGroups(deletedGroups);
    } else {
      let groupsCurrent = undefined;
      let deletedGroupsCurrent = undefined;

      let groupsTempByName = groups.filter((group) =>
        group.name.toLowerCase().includes(filterString.toLowerCase())
      );
      let deletedTempByName = deletedGroups.filter((group) =>
        group.name.toLowerCase().includes(filterString.toLowerCase())
      );

      if (groupsTempByName.length != 0) {
        groupsCurrent = groupsTempByName;
      }
      if (deletedTempByName.length != 0) {
        deletedGroupsCurrent = deletedTempByName;
      }

      let groupsTempBySchoolYear = groups.filter((group) =>
        group.schoolYear.includes(filterString)
      );
      let deletedGroupsTempBySchoolYear = deletedGroups.filter((group) =>
        group.schoolYear.includes(filterString)
      );

      if (groupsTempBySchoolYear.length != 0) {
        if (groupsCurrent !== undefined) {
          const newGroupsCurrent = [...groupsCurrent];

          groupsTempBySchoolYear.forEach(function (element) {
            const isPresent = groupsCurrent.some(
              (array) => JSON.stringify(array) === JSON.stringify(element)
            );
            if (!isPresent) {
              newGroupsCurrent.push(element);
            }
          });
          groupsCurrent = newGroupsCurrent;
        } else {
          groupsCurrent = groupsTempBySchoolYear;
        }
      }
      if (deletedGroupsTempBySchoolYear.length != 0) {
        if (deletedGroupsCurrent !== undefined) {
          const newDeletedGroupsCurrent = [...deletedGroupsCurrent];

          deletedGroupsTempBySchoolYear.forEach(function (element) {
            const isPresent = deletedGroupsCurrent.some(
              (array) => JSON.stringify(array) === JSON.stringify(element)
            );
            if (!isPresent) {
              newDeletedGroupsCurrent.push(element);
            }
          });
          deletedGroupsCurrent = newDeletedGroupsCurrent;
        } else {
          deletedGroupsCurrent = deletedGroupsTempBySchoolYear;
        }
      }

      let groupsTempByProgram = groups.filter((group) =>
        group.program.programName
          .toLowerCase()
          .includes(filterString.toLowerCase())
      );
      let deletedGroupsTempByProgram = deletedGroups.filter((group) =>
        group.program.programName
          .toLowerCase()
          .includes(filterString.toLowerCase())
      );

      if (groupsTempByProgram.length != 0) {
        if (groupsCurrent !== undefined) {
          const newGroupsCurrent = [...groupsCurrent];

          groupsTempByProgram.forEach(function (element) {
            const isPresent = groupsCurrent.some(
              (programs) => JSON.stringify(programs) === JSON.stringify(element)
            );
            if (!isPresent) {
              newGroupsCurrent.push(element);
            }
          });

          groupsCurrent = newGroupsCurrent;
        } else {
          groupsCurrent = groupsTempByProgram;
        }
      }
      if (deletedGroupsTempByProgram.length != 0) {
        if (deletedGroupsCurrent !== undefined) {
          const newDeletedGroupsCurrent = [...deletedGroupsCurrent];

          deletedGroupsTempByProgram.forEach(function (element) {
            const isPresent = deletedGroupsCurrent.some(
              (programs) => JSON.stringify(programs) === JSON.stringify(element)
            );
            if (!isPresent) {
              newDeletedGroupsCurrent.push(element);
            }
          });

          deletedGroupsCurrent = newDeletedGroupsCurrent;
        } else {
          deletedGroupsCurrent = deletedGroupsTempByProgram;
        }
      }

      if (groupsCurrent !== undefined) {
        setFilteredGroups(groupsCurrent);
      } else {
        setFilteredGroups([]);
      }

      if (deletedGroupsCurrent !== undefined) {
        setFilteredDeletedGroups(deletedGroupsCurrent);
      } else {
        setFilteredDeletedGroups([]);
      }
    }
    setPage(0);
    setPageInDeleted(0);
  };

  return (
    <div>
      <Container maxWidth="lg">
        <Grid container rowSpacing={3}>
          <Grid item sm={10}>
            <h3>Grupių sąrašas</h3>
          </Grid>
          <Grid item sm={2}>
            <Link to="/groups/create">
              <Stack direction="row" justifyContent="flex-end">
                <Button id="create-new-group" variant="contained">Pridėti naują</Button>
              </Stack>
            </Link>
          </Grid>

          <Grid item sm={12}>
            <TextField
              fullWidth
              variant="outlined"
              name="search-form"
              label="Paieška"
              id="search-form"
              onChange={(e) => handleSearch(e.target.value)}
            ></TextField>
          </Grid>
        </Grid>

        <TableContainer component={Paper}>
          <Table aria-label="custom pagination table">
            <TableHead>
              <TableRow>
                <TableCell style={{ width: "250px" }}>Grupė</TableCell>
                <TableCell style={{ width: "250px" }}>Programa</TableCell>
                <TableCell style={{ width: "250px" }}>Mokslo metai</TableCell>
                <TableCell>Studentų kiekis</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {(rowsPerPage > 0
                ? filteredGroups.slice(
                    page * rowsPerPage,
                    page * rowsPerPage + rowsPerPage
                  )
                : filteredGroups
              ).map((group) => (
                <TableRow key={group.id}>
                  <TableCell component="th" scope="row">
                    <Link to={"/groups/view/" + group.id}>{group.name}</Link>
                  </TableCell>
                  <TableCell>{group.program.programName}</TableCell>
                  <TableCell>{group.schoolYear}</TableCell>
                  <TableCell>{group.studentAmount}</TableCell>
                </TableRow>
              ))}

              {emptyRows > 0 && (
                <TableRow style={{ height: 53 * emptyRows }}>
                  <TableCell colSpan={6} />
                </TableRow>
              )}
            </TableBody>
            <TableFooter>
              <TableRow>
                <TablePagination
                  labelRowsPerPage="Rodyti po"
                  rowsPerPageOptions={[10, 20, { label: "Visi", value: -1 }]}
                  labelDisplayedRows={({ from, to, count }) =>
                    `${from}-${to} iš ${count}`
                  }
                  count={filteredGroups.length}
                  page={page}
                  SelectProps={{
                    inputProps: {
                      "aria-label": "Rodyti po",
                    },
                    native: true,
                  }}
                  onPageChange={handleChangePage}
                  rowsPerPage={rowsPerPage}
                  onRowsPerPageChange={handleChangeRowsPerPage}
                ></TablePagination>
              </TableRow>
            </TableFooter>
          </Table>
        </TableContainer>

        <FormGroup>
          <FormControlLabel
            control={<Checkbox />}
            label="Ištrintos grupės"
            onChange={(e) =>
              e.target.checked ? setChecked(true) : setChecked(false)
            }
          />
        </FormGroup>

        {isChecked && (
          <TableContainer component={Paper}>
            <Table aria-label="custom pagination table">
              <TableHead>
                <TableRow>
                  <TableCell style={{ width: "250px" }}>Grupė</TableCell>
                  <TableCell style={{ width: "250px" }}>Programa</TableCell>
                  <TableCell style={{ width: "250px" }}>Mokslo metai</TableCell>
                  <TableCell>Studentų kiekis</TableCell>
                  <TableCell></TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {(rowsPerPageInDeleted > 0
                  ? filteredDeletedGroups.slice(
                      pageInDeleted * rowsPerPageInDeleted,
                      pageInDeleted * rowsPerPageInDeleted +
                        rowsPerPageInDeleted
                    )
                  : filteredDeletedGroups
                ).map((group) => (
                  <TableRow key={group.id}>
                    <TableCell component="th" scope="row">
                      {group.name}
                    </TableCell>
                    <TableCell>{group.program.programName}</TableCell>
                    <TableCell>{group.schoolYear}</TableCell>
                    <TableCell>{group.studentAmount}</TableCell>
                    <TableCell className="activity">
                      <Button
                        variant="contained"
                        onClick={() => handleRestore(group.id)}
                      >
                        Atstatyti
                      </Button>
                    </TableCell>
                  </TableRow>
                ))}

                {emptyRowsInDeleted > 0 && (
                  <TableRow style={{ height: 53 * emptyRowsInDeleted }}>
                    <TableCell colSpan={6} />
                  </TableRow>
                )}
              </TableBody>

              <TableFooter>
                <TableRow>
                  <TablePagination
                    labelRowsPerPage="Rodyti po"
                    rowsPerPageOptions={[10, 20, { label: "Visi", value: -1 }]}
                    labelDisplayedRows={({ from, to, count }) =>
                      `${from}-${to} iš ${count}`
                    }
                    count={deletedGroups.length}
                    page={pageInDeleted}
                    SelectProps={{
                      inputProps: {
                        "aria-label": "Rodyti po",
                      },
                      native: true,
                    }}
                    onPageChange={handleChangePageInDeleted}
                    rowsPerPage={rowsPerPageInDeleted}
                    onRowsPerPageChange={handleChangeRowsPerPageInDeleted}
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
