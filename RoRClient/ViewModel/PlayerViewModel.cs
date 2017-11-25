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
		private ICommand createPlayerCommand;
		public ICommand CreatePlayerCommand
		{
			get
			{
				if (createPlayerCommand == null)
				{
					createPlayerCommand = new ActionCommand(param => CreateNewPlayerCommand());
				}
				return createPlayerCommand;
			}
		}

		private void CreateNewPlayerCommand()
		{
			Console.WriteLine("create player");
		}
	}
}
