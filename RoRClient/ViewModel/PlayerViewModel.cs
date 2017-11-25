using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;

namespace RoRClient.ViewModel
{
	class PlayerViewModel
	{
		public ICommand CreatePlayerCommand()
		{
			Console.WriteLine("create player");
			return null;
		}
	}
}
