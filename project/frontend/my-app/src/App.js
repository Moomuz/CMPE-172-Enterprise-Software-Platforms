// import template
import Navbar from './template/Navbar'

// import pages
import SignUp from './pages/SignUp'
import Home from './pages/Home'
import CustomerLogin from './pages/CustomerLogin'
import Menu from './pages/Menu'
import CustomerDashboard from './pages/CustomerDashboard'
import EmployeeDashboard from './pages/EmployeeDashboard'
import EmployeeLogin from './pages/EmployeeLogin'
import Payment from './pages/Payment'
// import css for App.js
import './App.css'

// import router
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'

function App() {
  return (
    <Router>
      <div className='App'>
        <Navbar />
        <div className='content'>
          {/* Ensures only 1 webpage is displayed */}
          <Switch>
            <Route path='/signup'>
              <SignUp />
            </Route>
          </Switch>
          <Switch>
            <Route path='/home'>
              <Home />
            </Route>
          </Switch>
          <Switch>
            <Route path='/customerlogin'>
              <CustomerLogin />
            </Route>
          </Switch>
          <Switch>
            <Route path='/employeelogin'>
              <EmployeeLogin />
            </Route>
          </Switch>
          <Switch>
            <Route path='/menu'>
              <Menu />
            </Route>
          </Switch>
          <Switch>
            <Route path='/customerdashboard'>
              <CustomerDashboard />
            </Route>
          </Switch>
          <Switch>
            <Route path='/employeedashboard'>
              <EmployeeDashboard />
            </Route>
          </Switch>
          <Switch>
            <Route path="/payment" render={(props) => <Payment {...props}/>}>
            </Route>
          </Switch>
        </div>
      </div>
    </Router>
  )
}

export default App
