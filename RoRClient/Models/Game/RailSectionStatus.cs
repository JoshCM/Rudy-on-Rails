using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace RoRClient.Models.Game
{
    /// <summary>
    /// Gibt an ob eine Railsection befahren werden kann/ darf.
    /// ACTIVE = Section darf befahren werden
    /// INACTIVE = Section kann nicht befahren werden, andere Umleitung durch aktive Section durch Weiche
    /// FORBIDDEN = Section ist befahrbar - befahren wird jedoch bestraft
    /// </summary>
    public enum RailSectionStatus
    {
        ACTIVE, INACTIVE, FORBIDDEN
    }
}



